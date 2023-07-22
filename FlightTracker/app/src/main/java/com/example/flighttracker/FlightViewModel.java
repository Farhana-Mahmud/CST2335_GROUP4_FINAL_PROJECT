package com.example.flighttracker;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FlightViewModel extends ViewModel {

    public List<Flight> flightList = new ArrayList<>();
}
