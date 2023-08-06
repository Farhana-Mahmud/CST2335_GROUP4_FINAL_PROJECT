package com.example.flighttracker.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.flighttracker.ChatMessage;

import java.util.ArrayList;
/**
 * ViewModel class for managing chat messages and the selected message.
 * This class is used to hold and handle data related to chat messages in the application.
 */
public class ConversionRoomViewModel extends ViewModel {
    /**
     * A MutableLiveData object holding an ArrayList of chat messages.
     * This LiveData is used to observe changes in the list of chat messages and update the UI accordingly.
     */
    public MutableLiveData<ArrayList<ChatMessage>> messages = new MutableLiveData<>();
    /**
     * A MutableLiveData object holding the currently selected chat message.
     * This LiveData is used to observe changes in the selected message and update the UI accordingly.
     */
    public MutableLiveData<ChatMessage> selectedMessage = new MutableLiveData< >();

}
