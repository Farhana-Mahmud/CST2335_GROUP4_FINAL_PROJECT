package com.example.flighttracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
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

public class FavouriteFlights extends AppCompatActivity implements OnItemClickListener,OnLongClickListener,PositiveClickListener {

    private FlightAdapter flightAdapter;
    private ActivityFavouritelistingBinding favouritelistingBinding;

    FlightDatabase flightDatabase;
    FlightDao flightDao;
    List<Flight> flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        favouritelistingBinding = DataBindingUtil.setContentView(this, R.layout.activity_favouritelisting);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favouritelistingBinding.rvFights.setLayoutManager(linearLayoutManager);
        flightAdapter = new FlightAdapter(new ArrayList<>());
        flightAdapter.setOnItemClickListener(this::onItemClickListener);
        flightAdapter.setOnLongClickListener(this::onLongClickListener);
        favouritelistingBinding.rvFights.setAdapter(flightAdapter);
        flightDatabase = Room.databaseBuilder(FavouriteFlights.this, FlightDatabase.class, API_KEYS.DATABASE_NAME).build();
        flightDao = flightDatabase.flightDao();
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            flights = flightDao.getFlights();
            if (flights.size() == 0) {
                runOnUiThread(() -> {
                    MainActivity.createToast(FavouriteFlights.this, getResources().getString(R.string.no_record_found));
                    FavouriteFlights.this.finish();
                });
            } else {
                runOnUiThread(() -> {
                    flightAdapter.setData(flights);
                });
            }
        });
    }


    @Override
    public void onItemClickListener(Flight flight, int position) {

        Intent intent = new Intent(this, FlightDetail.class);
        intent.putExtra(API_KEYS.FLIGHT_DETAIL, flight);
        startActivity(intent);

    }

    @Override
    public void onLongClickListener(Flight flight, int position) {
        MainActivity.showAlertDialog(this, getResources().getString(R.string.do_you_want_delete_record) + position, getResources().getString(R.string.please_confirm), new String[]{getResources().getString(R.string.yes), getResources().getString(R.string.cancel)}, this, flight);

    }

    @Override
    public void onUserConfirmation(Flight flight) {
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            flightDao.deleteFlight(flight.flight_id);
            runOnUiThread(() -> {
                MainActivity.snackBar(this,
                        getResources().getString(R.string.selected_record_deleted_successfully), favouritelistingBinding.getRoot());
                flights.remove(flight);
                flightAdapter.setData(flights);
            });
        });


    }

}