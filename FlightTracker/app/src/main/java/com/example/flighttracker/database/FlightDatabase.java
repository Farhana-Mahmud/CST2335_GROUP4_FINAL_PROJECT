package com.example.flighttracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.flighttracker.API_KEYS;
import com.example.flighttracker.Flight;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Flight.class}, version = 1, exportSchema = false)
public abstract class FlightDatabase  extends RoomDatabase {
    public abstract FlightDao flightDao();

}