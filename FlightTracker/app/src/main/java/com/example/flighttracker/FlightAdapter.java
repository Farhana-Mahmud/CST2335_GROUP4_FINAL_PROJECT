package com.example.flighttracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flighttracker.databinding.FlightListItemBinding;
import com.example.flighttracker.generated.callback.OnClickListener;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.FlightViewHolder> {

    private List<Flight> data;
    private OnItemClickListener onItemClickListener;
    private OnLongClickListener onLongClickListener;

    public FlightAdapter(List<Flight>data)
    {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        this.onLongClickListener = onLongClickListener;
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(onLongClickListener!=null)
                {
                    onLongClickListener.onLongClickListener(flight,position);
                }
                return true;
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null)
                {
                    onItemClickListener.onItemClickListener(flight,position);
                }
            }
        });
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
