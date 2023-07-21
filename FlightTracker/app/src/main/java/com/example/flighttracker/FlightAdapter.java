package com.example.flighttracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flighttracker.databinding.FlightListItemBinding;

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
        FlightListItemBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.flight_list_item,parent,false);
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

    public void setData(List<Flight> flights) {
        this.data.clear();
        this.data=flights;
        notifyDataSetChanged();
    }

    static class FlightViewHolder extends  RecyclerView.ViewHolder
    {
        FlightListItemBinding flightListItemBinding;
        public FlightViewHolder(@NonNull FlightListItemBinding flightListItemBinding) {
            super(flightListItemBinding.getRoot());
           this.flightListItemBinding =flightListItemBinding;

        }
        void onBind(Flight flight)
        {

            flightListItemBinding.txtFlightNoValue.setText(flight.getmFlightNumber());


        }

    }

}
