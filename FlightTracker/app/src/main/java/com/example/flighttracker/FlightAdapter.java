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
//import com.example.flighttracker.generated.callback.OnClickListener;

import java.util.List;

/**
 * @author Farhana Mahmud
 * @version 1.0
 */
public class FlightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    /**
     * This saves the list of flight type objects
     */
    private List<Flight> data;
    /**
     * This variable denotes if a flight is a favourite or not
     */
    Boolean isFavourite;
    /**
     * Initialize OnItemClickListener
     */
    private OnItemClickListener onItemClickListener;
    /**
     * Initialize OnDeleteClickListener
     */
    private OnDeleteClickListener onDeleteClickListener;

    /**
     * Deletes data from the flight list
     * @param onDeleteClickListener
     */
    public void setOnDeleteClickListener(OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
    }

    /**
     * Saves a flight into favourites
     * @param data is the list of flights
     * @param isFavourite if added
     */
    public FlightAdapter(List<Flight>data, boolean isFavourite)
    {
        this.data = data;
        this.isFavourite = isFavourite;
    }

    /**
     * This methods sets if a flight as favourite
     * @param favourite
     */
    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    /**
     * Sets the attribute of a flight
     * @param onItemClickListener
     */
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
