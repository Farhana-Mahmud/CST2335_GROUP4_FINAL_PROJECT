package com.example.flighttracker;

/**
 * This is an interface to handle the delete function from the database
 */
public interface OnDeleteClickListener {

    void onDeleteClickListener(Flight flight,int position);
}
