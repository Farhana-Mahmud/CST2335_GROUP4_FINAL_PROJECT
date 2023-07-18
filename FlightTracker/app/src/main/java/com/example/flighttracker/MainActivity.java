package com.example.flighttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    RecyclerView flight_list;
    List<Flight> flights;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        flight_list = findViewById(R.id.rv_fights);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        flights = getFlights();
        flight_list.setLayoutManager(linearLayoutManager);
        FlightAdapter flightAdapter = new FlightAdapter(flights);
        flight_list.setAdapter(flightAdapter);

    }

    List<Flight> getFlights() {
        List<Flight> data = new ArrayList<>();
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        data.add(new Flight("A","B","Schedule"));
        return data;
    }
}