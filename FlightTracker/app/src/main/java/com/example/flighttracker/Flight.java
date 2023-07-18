package com.example.flighttracker;

public class Flight {
    private String departure_airport,arrival_airport,status;

    public Flight(String departure_airport, String arrival_airport, String status) {
        this.departure_airport = departure_airport;
        this.arrival_airport = arrival_airport;
        this.status = status;
    }

    public String getDeparture_airport() {
        return departure_airport;
    }



    public String getArrival_airport() {
        return arrival_airport;
    }



    public String getStatus() {
        return status;
    }


}
