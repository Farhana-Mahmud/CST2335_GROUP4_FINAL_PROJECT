package com.example.flighttracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.flighttracker.database.FlightDao;
import com.example.flighttracker.database.FlightDatabase;
import com.example.flighttracker.databinding.ActivityFlightDetailBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class FlightDetail extends Fragment implements View.OnClickListener {


    ActivityFlightDetailBinding flightDetailBinding;

    FlightDatabase flightDatabase;
    FlightDao flightDao;
    Flight flight;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        flightDetailBinding = ActivityFlightDetailBinding.inflate(inflater,container,false);
        return flightDetailBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flightDetailBinding.setOnClick(this::onClick);
        flightDatabase = Room.databaseBuilder(getContext(),FlightDatabase.class,API_KEYS.DATABASE_NAME).build();
        flightDao = flightDatabase.flightDao();
        flight = getArguments().getParcelable(API_KEYS.FLIGHT_DETAIL);
        int saveIsVisible = getArguments().getInt(API_KEYS.FRAGMENT_FAVOURITE,0);
        if(saveIsVisible==1)
        {
            flightDetailBinding.btnAdd.setVisibility(View.GONE);
        }
        setHasOptionsMenu(true);
        if(flight!=null)
        {
            fillFlightDetail(flight);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.help_menu_detail,menu);

    }

    void fillFlightDetail(Flight flight)
    {
        flightDetailBinding.txtFlightNoValue.setText(flight.getmFlightNumber());
        flightDetailBinding.txtTerminalValue.setText(flight.getmTerminal());
        flightDetailBinding.txtGateValue.setText(flight.getmGate());
        flightDetailBinding.txtDepAirportValue.setText(flight.getDeparture_airport());
        flightDetailBinding.txtAirportArrival.setText(flight.getArrival_airport());
        flightDetailBinding.txtFlightStatus.setText(flight.getStatus());
        flightDetailBinding.txtDelay.setText(flight.getmDelay());
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==R.id.btn_add)
        {
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(()->{
                flightDao.insert(flight);
                getActivity().runOnUiThread(
                        ()->MainActivity.snackBar(getContext(),getResources().getString(R.string.add_into_favourite),flightDetailBinding.getRoot()));
                // Adds to database
                getActivity().getSupportFragmentManager().popBackStack();
            });
        }

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.menu_help)
        {
            MainActivity.showAlertDialog(getContext(),getResources().getString(R.string.detail_help_title),getResources().getString(R.string.detail_help_message),
                    new String[]{getResources().getString(R.string.main_positive_button_label),
                            getResources().getString(R.string.main_negative_button_label)},null,null);
        }

        return true;
    }

}