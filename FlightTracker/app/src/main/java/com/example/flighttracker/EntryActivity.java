package com.example.flighttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.flighttracker.databinding.ActivityEntryBinding;

public class EntryActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityEntryBinding activityEntryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEntryBinding = DataBindingUtil.setContentView(this,R.layout.activity_entry);
        activityEntryBinding.setOnClick(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_flight_tracker)
        {
            Intent intent = new Intent(EntryActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else if(view.getId()==R.id.btn_currency_converter)
        {
            Intent intent = new Intent(EntryActivity.this, FlightDetail.class);
            startActivity(intent);
        }



    }


}