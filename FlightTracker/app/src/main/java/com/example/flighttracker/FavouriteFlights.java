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
import com.example.flighttracker.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavouriteFlights extends AppCompatActivity implements OnItemClickListener {

    private FlightAdapter flightAdapter;
    private ActivityMainBinding activityMainBinding;

    FlightDatabase flightDatabase;
    FlightDao flightDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        activityMainBinding.rvFights.setLayoutManager(linearLayoutManager);
        activityMainBinding.pbLoader.setVisibility(View.GONE);
        flightAdapter = new FlightAdapter(new ArrayList<>());
        flightAdapter.setOnItemClickListener(this::onItemClickListener);
        activityMainBinding.rvFights.setAdapter(flightAdapter);
        flightDatabase = Room.databaseBuilder(FavouriteFlights.this, FlightDatabase.class,API_KEYS.DATABASE_NAME).build();
        flightDao = flightDatabase.flightDao();
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(()->{
            List<Flight> flights = flightDao.getFlights();
            if(flights.size()==0)
            {
                runOnUiThread(()-> {
                    MainActivity.createToast(FavouriteFlights.this,getResources().getString(R.string.no_record_found));
                    FavouriteFlights.this.finish();
                });
            }
            else {
                runOnUiThread(()->{
                    flightAdapter.setData(flights);
                });
            }
        });
    }






    @Override
    public void onItemClickListener(Flight flight, int position) {

        Intent intent = new Intent(this,FlightDetail.class);
        intent.putExtra(API_KEYS.FLIGHT_DETAIL,flight);
        startActivity(intent);

    }
}
