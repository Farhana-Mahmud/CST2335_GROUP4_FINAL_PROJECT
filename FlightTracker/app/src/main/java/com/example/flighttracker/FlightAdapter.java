package com.example.flighttracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flighttracker.databinding.FlightListItemBinding;
import com.example.flighttracker.databinding.FlightListItemWithDeleteBinding;
import com.example.flighttracker.generated.callback.OnClickListener;

import java.util.List;

public class FlightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Flight> data;
    Boolean isFavourite;
    private OnItemClickListener onItemClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    public FlightAdapter(List<Flight>data, boolean isFavourite)
    {
        this.data = data;
        this.isFavourite = isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(isFavourite){
            FlightListItemWithDeleteBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.flight_list_item_with_delete,parent,false);
            return new FlightViewHolderWithDelete(view);
        }
        else
        {
            FlightListItemBinding view = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.flight_list_item,parent,false);
            return new FlightViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Flight flight = data.get(position);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null)
                {
                    onItemClickListener.onItemClickListener(flight,position);
                }
            }
        });
        if(holder instanceof FlightViewHolder)
        {
            ((FlightViewHolder)holder).onBind(flight);
        }
        else
        {


            ((FlightViewHolderWithDelete)holder).onBind(flight);
            ((FlightViewHolderWithDelete)holder).flightListItemBinding.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onDeleteClickListener!=null)
                    {
                        onDeleteClickListener.onDeleteClickListener(flight,position);
                    }
                }
            });

        }

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

    static class FlightViewHolderWithDelete extends  RecyclerView.ViewHolder
    {
        FlightListItemWithDeleteBinding flightListItemBinding;
        public FlightViewHolderWithDelete(@NonNull FlightListItemWithDeleteBinding flightListItemBinding) {
            super(flightListItemBinding.getRoot());
            this.flightListItemBinding =flightListItemBinding;

        }
        void onBind(Flight flight)
        {

            flightListItemBinding.txtFlightNoValue.setText(flight.getmFlightNumber());



        }

    }

}
