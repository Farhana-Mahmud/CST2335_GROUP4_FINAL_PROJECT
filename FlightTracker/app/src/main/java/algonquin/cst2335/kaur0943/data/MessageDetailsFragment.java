package algonquin.cst2335.kaur0943.data;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flighttracker.databinding.ActivityChatRoom1Binding;
import com.example.flighttracker.databinding.Details1LayoutBinding;

import algonquin.cst2335.kaur0943.ChatMessage;


/**
 * A fragment that displays the details of a selected chat message.
 * This fragment is responsible for showing the message and its details in the chat room activity.
 */
public class MessageDetailsFragment extends Fragment {
    ChatMessage selected;
    /**
     * Called to have the fragment instantiate its user interface view.
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment.
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     * @return Return the View for the fragment's UI.
     */
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Details1LayoutBinding binding=Details1LayoutBinding.inflate(inflater);
        ActivityChatRoom1Binding binding1=ActivityChatRoom1Binding.inflate(inflater);
        binding.message.setText(selected.message);
        binding.time.setText(selected.timeSent);
        binding.textView3.setText(selected.message);

        binding.textView4.setText("Id ="+selected.id);
        return binding.getRoot();
    }
    public MessageDetailsFragment(ChatMessage m){
        selected=m;
    }


}
