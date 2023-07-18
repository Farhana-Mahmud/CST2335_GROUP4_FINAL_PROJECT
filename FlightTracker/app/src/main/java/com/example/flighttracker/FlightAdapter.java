package com.example.flighttracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {

    private List<Flight> data;

    public FlightAdapter(List<Flight>data)
    {
        this.data = data;
    }


    @NonNull
    @Override
    public FlightViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_list_item,parent,false);
        return new FlightViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightViewHolder holder, int position) {
        Flight flight = data.get(position);
        holder.onBind(flight);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

   static class FlightViewHolder extends  RecyclerView.ViewHolder
    {
        TextView departure_airport,arrival_airport,flight_status;
        public FlightViewHolder(@NonNull View itemView) {
            super(itemView);
           departure_airport = itemView.findViewById(R.id.txt_flight_airport_value);
           arrival_airport = itemView.findViewById(R.id.txt_apprival_airport_value);
           flight_status = itemView.findViewById(R.id.txt_flight_status_value);
        }
        void onBind(Flight flight)
        {
            departure_airport.setText(flight.getDeparture_airport());
            arrival_airport.setText(flight.getArrival_airport());
            flight_status.setText(flight.getStatus());

        }

    }

}
