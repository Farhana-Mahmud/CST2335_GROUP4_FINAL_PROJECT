package com.example.flighttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flighttracker.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    List<Flight> flights;
    ActivityMainBinding activityMainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        createToast(this,"Welcome to Flight Tracking");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        flights = getFlights();

        SharedPreferenceHelper.initialize(MainActivity.this,"flight_tracker_preference");
        String earlier_SearchTerm = SharedPreferenceHelper.getStringValue("SEARCH_TERM");
        if(earlier_SearchTerm!=null && earlier_SearchTerm!="")
        {
            activityMainBinding.editSearch.setText(earlier_SearchTerm);
        }


        activityMainBinding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(activityMainBinding.editSearch.getText().toString()))
                {
                    showAlertDialog(MainActivity.this,"Airport code is required","Please enter Airport code",new String[]{"Ok","Cancel"});
                }
                else
                {
                    snackBar(MainActivity.this,"Enter Value is"+activityMainBinding.editSearch.getText().toString(),activityMainBinding.editSearch);

                    SharedPreferenceHelper.setStringValue("SEARCH_TERM",activityMainBinding.editSearch.getText().toString());
                }

            }
        });
        activityMainBinding.rvFights.setLayoutManager(linearLayoutManager);
        FlightAdapter flightAdapter = new FlightAdapter(flights);
        activityMainBinding.rvFights.setAdapter(flightAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.help_menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.menu_help)
        {
            showAlertDialog(this,getResources().getString(R.string.main_help_title),getResources().getString(R.string.main_help_message),
                    new String[]{getResources().getString(R.string.main_positive_button_label),
                            getResources().getString(R.string.main_negative_button_label)});
        }
        else if(item.getItemId()==R.id.menu_favourite)
        {

        }
        return true;
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


    void createToast(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    void snackBar(Context context, String message, View view)
    {
        Snackbar.make(context,view,message,Snackbar.LENGTH_LONG).show();
    }

    void showAlertDialog(Context context,String title,String message,String[] buttonTitles)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                                .setTitle(title).setMessage(message).setPositiveButton(buttonTitles[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setNegativeButton(buttonTitles[1], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}