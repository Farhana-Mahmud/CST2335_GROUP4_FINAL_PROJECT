package TriviaDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.flighttracker.R;

import java.util.List;

/**
 *
 * this class has a adapter to show the high scorer's name saved in database
 * @author afnan
 */

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.ViewHolder> {

    private List<HighScore> highScoresList;
    private HighScoreItemClickListener itemClickListener;


    /**
     *
     * @param highScoresList list of high scores
     * @param itemClickListener  shows the item
     */
    public HighScoreAdapter(List<HighScore> highScoresList, HighScoreItemClickListener itemClickListener) {
        this.highScoresList = highScoresList;
        this.itemClickListener = itemClickListener;
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return ViewHolder
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_high_score, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HighScore highScore = highScoresList.get(position);
        holder.textViewPlayerName.setText(highScore.getPlayerName());
        holder.textViewScore.setText(String.valueOf(highScore.getScore()));

        // Set an OnClickListener to handle item clicks
        holder.itemView.setOnClickListener(v -> {
            if (itemClickListener != null) {
                itemClickListener.onHighScoreItemClick(highScore.getPlayerName());
            }
        });
    }


    /**
     *
     * @return highScoresList size
     */
    @Override
    public int getItemCount() {
        return highScoresList.size();
    }


    /**
     * viewholder class extend the recyler view to show the high scorer in a scrollable view
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        /**
         *
         */
        TextView textViewPlayerName;
        /**
         *
         */
        TextView textViewScore;


        /**
         *
         * @param itemView shows the name of users
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewPlayerName = itemView.findViewById(R.id.textViewPlayerName);
            textViewScore = itemView.findViewById(R.id.textViewScore);
        }
    }

    /**
     * Interface for item click events
     */
    public interface HighScoreItemClickListener {
        /**
         *
         * @param highScorerName set the click
         */
        void onHighScoreItemClick(String highScorerName);
    }
}
