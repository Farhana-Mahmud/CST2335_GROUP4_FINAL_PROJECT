package com.example.flighttracker;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.versionedparcelable.ParcelField;

import org.json.JSONException;
import org.json.JSONObject;

@Entity(tableName = "flight_table")

public class Flight implements Parcelable {

    public String mFlightNumber,mTerminal,mGate, mDelay, departure_airport,arrival_airport,status;
    @PrimaryKey(autoGenerate = true)
    @NonNull
    public Long flight_id;
    public Flight(String departure_airport, String arrival_airport, String status, String mFlightNumber, String mTerminal, String mGate, String mdelay) {
        this.departure_airport = departure_airport;
        this.arrival_airport = arrival_airport;
        this.status = status;
        this.mFlightNumber = mFlightNumber;
        this.mTerminal = mTerminal;
        this.mGate = mGate;
        this.mDelay = mdelay;
    }
    public Flight(Long id,String departure_airport, String arrival_airport, String status, String mFlightNumber, String mTerminal, String mGate, String mdelay) {
        this.flight_id=id;
        this.departure_airport = departure_airport;
        this.arrival_airport = arrival_airport;
        this.status = status;
        this.mFlightNumber = mFlightNumber;
        this.mTerminal = mTerminal;
        this.mGate = mGate;
        this.mDelay = mdelay;
    }

    public Flight() {

    }

    protected Flight(Parcel in) {
        mFlightNumber = in.readString();
        mTerminal = in.readString();
        mGate = in.readString();
        mDelay = in.readString();
        departure_airport = in.readString();
        arrival_airport = in.readString();
        status = in.readString();
        if (in.readByte() == 0) {
            flight_id = null;
        } else {
            flight_id = in.readLong();
        }
    }

    public static final Creator<Flight> CREATOR = new Creator<Flight>() {
        @Override
        public Flight createFromParcel(Parcel in) {
            return new Flight(in);
        }

        @Override
        public Flight[] newArray(int size) {
            return new Flight[size];
        }
    };

    @NonNull
    public Long getFlight_id() {
        return flight_id;
    }

    public Flight convertJsonToFlight(JSONObject jsonObject) throws JSONException {
            JSONObject flight_detail = jsonObject.getJSONObject(API_KEYS.FLIGHT);
            JSONObject departure = jsonObject.getJSONObject(API_KEYS.DEPARTURE);
            JSONObject arrival = jsonObject.getJSONObject(API_KEYS.ARRIVAL);
            JSONObject airline = jsonObject.getJSONObject(API_KEYS.AIRLINE);
            String mFlightNumber,
                mTerminal,mGate,
                mDelay, departure_airport,arrival_airport,status;
            mFlightNumber = flight_detail.getString(API_KEYS.NUMBER);
            departure_airport = departure.getString(API_KEYS.AIRPORT);
            arrival_airport = arrival.getString(API_KEYS.AIRPORT);
            mTerminal = departure.getString(API_KEYS.TERMINAL);
            mGate = departure.getString(API_KEYS.GATE);
            status =jsonObject.getString(API_KEYS.FLIGHT_STATUS);
            mDelay =departure.getString(API_KEYS.DELAY);
            Flight flight = new Flight(departure_airport,arrival_airport,status,mFlightNumber,mTerminal,mGate,mDelay);
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

    public String getmDelay() {
        return mDelay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(mFlightNumber);
        parcel.writeString(mTerminal);
        parcel.writeString(mGate);
        parcel.writeString(mDelay);
        parcel.writeString(departure_airport);
        parcel.writeString(arrival_airport);
        parcel.writeString(status);
        if (flight_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(flight_id);
        }
    }
}
