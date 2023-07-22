package com.example.flighttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;

import com.example.flighttracker.database.FlightDao;
import com.example.flighttracker.database.FlightDatabase;
import com.example.flighttracker.databinding.ActivityFlightDetailBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FlightDetail extends AppCompatActivity implements View.OnClickListener {


    FlightViewModel flightViewModel;
    ActivityFlightDetailBinding flightDetailBinding;
    FlightDatabase flightDatabase;
    FlightDao flightDao;
    Flight flight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        flightDetailBinding = DataBindingUtil.setContentView(this,R.layout.activity_flight_detail);
        flightDetailBinding.setOnClick(this::onClick);
        flightViewModel = new ViewModelProvider(this).get(FlightViewModel.class);
        flightDatabase = Room.databaseBuilder(this,FlightDatabase.class,API_KEYS.DATABASE_NAME).build();
        flightDao = flightDatabase.flightDao();
        flight = getIntent().getExtras().getParcelable(API_KEYS.FLIGHT_DETAIL);
        if(flight!=null)
        {
            fillFlightDetail(flight);
        }
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
                runOnUiThread(()->MainActivity.snackBar(FlightDetail.this,getResources().getString(R.string.add_into_favourite),flightDetailBinding.getRoot()));// Adds to database
            });
        }

    }
}