package com.example.flighttracker.data;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flighttracker.R;

/**
 * The main activity of the application.
 * This activity serves as the entry point of the application and is responsible for displaying the main user interface.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is starting.
     * This is where most initialization of the activity should go, such as inflating the layout and setting up the user interface.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied in onSaveInstanceState(Bundle).
     *                           Note: Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}