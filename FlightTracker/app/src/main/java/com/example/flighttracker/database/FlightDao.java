package com.example.flighttracker.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.flighttracker.Flight;

import java.util.List;

@Dao
public interface FlightDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Flight flight);

    @Update
    void update(Flight flight);

    @Query("SELECT * from flight_table ORDER By flight_id Asc")
    List<Flight> getFlights();

    @Query("DELETE FROM flight_table WHERE flight_id = :flightId")
    int deleteFlight(long flightId);
    @Query("DELETE from flight_table")
    void deleteAll();
}



