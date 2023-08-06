package TriviaDatabase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.flighttracker.R;


/**
 *
 * This class shows users scores with a fragment
 * @author afnan
 */
public class HighScorerNameFragment extends Fragment {

    private static final String ARG_HIGH_SCORER_NAME = "highScorerName";

    /**
     *
     * @param highScorerName name of high scores
     * @return fragment showing the name of users with their scores
     */
    public static HighScorerNameFragment newInstance(String highScorerName) {
        HighScorerNameFragment fragment = new HighScorerNameFragment();
        Bundle args = new Bundle();
        args.putString(ARG_HIGH_SCORER_NAME, highScorerName);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return rootView showing the name of high scorers
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_high_scorer_name, container, false);
        TextView textViewHighScorerName = rootView.findViewById(R.id.textViewHighScorerName);

        if (getArguments() != null) {
            String highScorerName = getArguments().getString(ARG_HIGH_SCORER_NAME);
            textViewHighScorerName.setText(highScorerName);
        }

        return rootView;
    }
}
