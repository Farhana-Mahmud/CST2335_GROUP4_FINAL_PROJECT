package com.example.flighttracker.entry;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.flighttracker.MainActivity;
import com.example.flighttracker.R;
import com.example.flighttracker.databinding.ActivityEntryBinding;

import algonquin.cst2335.kaur0943.CurrencyConverter;


public class EntryActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityEntryBinding activityEntryBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityEntryBinding = DataBindingUtil.setContentView(this, R.layout.activity_entry);
        activityEntryBinding.setOnClick(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_entry,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.menu_help)
        {
            MainActivity.showAlertDialog(this,getResources().getString(R.string.entry_help_title),getResources().getString(R.string.entry_help_detail),new String[]{getResources().getString(R.string.ok),getResources().getString(R.string.cancel)},null,null);
        }
        else if(item.getItemId()==R.id.menu_flight)
        {
            Intent intent = new Intent(EntryActivity.this, MainActivity.class);
            startActivity(intent);
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.btn_flight_tracker)
        {
            Intent intent = new Intent(EntryActivity.this, MainActivity.class);
            startActivity(intent);
        } else if (view.getId()==R.id.btn_trivia_quiz) {
            Intent intent = new Intent(EntryActivity.this, CurrencyConverter.class);
            startActivity(intent);
        }


    }


}