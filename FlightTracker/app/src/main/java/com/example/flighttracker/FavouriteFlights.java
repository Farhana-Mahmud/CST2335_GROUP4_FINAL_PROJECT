package com.example.flighttracker;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;


import com.example.flighttracker.database.FlightDao;
import com.example.flighttracker.database.FlightDatabase;
import com.example.flighttracker.databinding.ActivityFavouritelistingBinding;
import com.example.flighttracker.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavouriteFlights extends Fragment implements OnItemClickListener, OnLongClickListener, PositiveClickListener {

    private FlightAdapter flightAdapter;
    private ActivityFavouritelistingBinding favouritelistingBinding;

    FlightDatabase flightDatabase;
    FlightDao flightDao;

    FlightViewModel flightViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        favouritelistingBinding = DataBindingUtil.inflate(inflater, R.layout.activity_favouritelisting, container, false);
        setHasOptionsMenu(true);
        return favouritelistingBinding.getRoot();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
            menu.clear();
            inflater.inflate(R.menu.help_menu_detail,menu);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favouritelistingBinding = DataBindingUtil.setContentView(getActivity(), R.layout.activity_favouritelisting);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        favouritelistingBinding.rvFights.setLayoutManager(linearLayoutManager);
        flightViewModel = new ViewModelProvider(this).get(FlightViewModel.class);
        flightAdapter = new FlightAdapter(new ArrayList<>());
        flightAdapter.setOnItemClickListener(this::onItemClickListener);
        flightAdapter.setOnLongClickListener(this::onLongClickListener);
        favouritelistingBinding.rvFights.setAdapter(flightAdapter);
        flightDatabase = Room.databaseBuilder(getContext(), FlightDatabase.class, API_KEYS.DATABASE_NAME).build();
        flightDao = flightDatabase.flightDao();
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            flightViewModel.flightList.addAll(flightDao.getFlights());
                getActivity().runOnUiThread(() -> {
                    flightAdapter.setData(flightViewModel.flightList);
                });

        });
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.menu_help)
        {
            MainActivity.showAlertDialog(getContext(),getResources().getString(R.string.favourite_help_title),getResources().getString(R.string.favourite_help_message),
                    new String[]{getResources().getString(R.string.main_positive_button_label),
                            getResources().getString(R.string.main_negative_button_label)},null,null);
        }

        return true;
    }


    @Override
    public void onItemClickListener(Flight flight, int position) {

        Fragment fragment = new FlightDetail();
        Bundle bundle = new Bundle();
        bundle.putParcelable(API_KEYS.FLIGHT_DETAIL,flight);
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.container,fragment).commit();

    }

    @Override
    public void onLongClickListener(Flight flight, int position) {
        MainActivity.showAlertDialog(getContext(), getResources().getString(R.string.do_you_want_delete_record) + position, getResources().getString(R.string.please_confirm), new String[]{getResources().getString(R.string.yes), getResources().getString(R.string.cancel)}, this, flight);

    }

    @Override
    public void onUserConfirmation(Flight flight) {
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            flightDao.deleteFlight(flight.flight_id);

            flightViewModel.flightList.remove(flight);

            getActivity().runOnUiThread(()->{
                flightAdapter.notifyDataSetChanged();
            });

                MainActivity.snackBar(getContext(), getResources().getString(R.string.selected_record_deleted_successfully), favouritelistingBinding.getRoot());


        });


    }

}