package com.example.flighttracker;

import org.json.JSONException;
import org.json.JSONObject;

public class Flight {

    private String mFlightNumber,mTerminal,mGate,mdelay, departure_airport,arrival_airport,status;

    public Flight(String departure_airport, String arrival_airport, String status, String mFlightNumber, String mTerminal, String mGate, String mdelay) {
        this.departure_airport = departure_airport;
        this.arrival_airport = arrival_airport;
        this.status = status;
        this.mFlightNumber = mFlightNumber;
        this.mTerminal = mTerminal;
        this.mGate = mGate;
        this.mdelay = mdelay;
    }

    public Flight() {

    }

    public Flight convertJsonToFlight(JSONObject jsonObject) throws JSONException {
            JSONObject flight_detail = jsonObject.getJSONObject(API_KEYS.FLIGHT);
            JSONObject departure = jsonObject.getJSONObject(API_KEYS.DEPARTURE);
            JSONObject arrival = jsonObject.getJSONObject(API_KEYS.ARRIVAL);
            JSONObject airline = jsonObject.getJSONObject(API_KEYS.AIRLINE);
            String mFlightNumber,
                mTerminal,mGate,
                mdelay, departure_airport,arrival_airport,status;
            mFlightNumber = flight_detail.getString(API_KEYS.NUMBER);
            departure_airport = departure.getString(API_KEYS.AIRPORT);
            arrival_airport = arrival.getString(API_KEYS.AIRPORT);
            mTerminal = departure.getString(API_KEYS.TERMINAL);
            mGate = departure.getString(API_KEYS.GATE);
            status =jsonObject.getString(API_KEYS.FLIGHT_STATUS);
            mdelay =departure.getString(API_KEYS.DELAY);
            Flight flight = new Flight(departure_airport,arrival_airport,status,mFlightNumber,mTerminal,mGate,mdelay);
            return flight;
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

    public String getmFlightNumber() {
        if(mFlightNumber==null || mFlightNumber.isEmpty() || mFlightNumber=="null")
        {
            return "Missing";
        }
        return mFlightNumber;
    }


    public String getmTerminal() {
        return mTerminal;
    }

    public String getmGate() {
        return mGate;
    }

    public String getMdelay() {
        return mdelay;
    }
}
