package algonquin.cst2335.kaur0943.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import algonquin.cst2335.kaur0943.ChatMessage;
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
