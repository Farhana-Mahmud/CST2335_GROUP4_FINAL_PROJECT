package com.example.flighttracker;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Farhana Mahmud
 * @version 1.0
 */
public class FlightViewModel extends ViewModel {

    /**
     * Initializing flight list with objects of Flight type
     */
    public List<Flight> flightList = new ArrayList<>();
}
