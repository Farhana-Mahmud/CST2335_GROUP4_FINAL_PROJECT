package TriviaDatabase;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flighttracker.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class creates save the name of users in the database, also set on click listener for button
 * to show the name of high scorers
 * @author afnan
 */
public class HighScoreActivity extends AppCompatActivity implements HighScoreAdapter.HighScoreItemClickListener {

    private RecyclerView recyclerView;
    private HighScoreAdapter adapter;
    private List<HighScore> highScoresList;

    private AppDatabase appDatabase; // Room database instance


    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.high_score_activity);

        // Initialize the Room database instance
        appDatabase = AppDatabase.getInstance(this);

        // Find the RecyclerView in the layout
        recyclerView = findViewById(R.id.recyclerViewHighScores);

        // Create an instance of LinearLayoutManager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the highScoresList
        highScoresList = new ArrayList<>();

        // Create an instance of the HighScoreAdapter and set it to the RecyclerView
        adapter = new HighScoreAdapter(highScoresList, this);
        recyclerView.setAdapter(adapter);

        // Load and display the high scores from the Room database
        loadHighScoresFromDatabase();
    }


    /**
     * shows the scorer's name by clicking the button
     * @param highScorerName  name of users who scored highest
     */
    @Override
    public void onHighScoreItemClick(String highScorerName) {
        // Show the HighScorerNameFragment when a high score is clicked
        showHighScorerNameFragment(highScorerName);
    }

    /**
     *
     * @param highScorerName a fragment manager for showing the name of users
     *                       who has ranked from highest to lowest
     */
    private void showHighScorerNameFragment(String highScorerName) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Find the existing fragment by tag and remove it if it exists
        Fragment existingFragment = fragmentManager.findFragmentByTag("HighScorerNameFragment");
        if (existingFragment != null) {
            fragmentTransaction.remove(existingFragment);
        }

        HighScorerNameFragment fragment = HighScorerNameFragment.newInstance(highScorerName);

        // Replace the fragment container with the HighScorerNameFragment
        fragmentTransaction.replace(R.id.fragmentContainer, fragment, "HighScorerNameFragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }






    // Method to display the top 10 high scores from the database
    private void showTopHighScores(List<HighScore> topHighScores) {
        // Update the UI to display the top high scores
        highScoresList.clear();
        highScoresList.addAll(topHighScores);
        adapter.notifyDataSetChanged();
    }

    // Method to show an AlertDialog and ask for the user's name
    private void showNameDialog(int score) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Name");
        final EditText input = new EditText(this);
        builder.setView(input);
        builder.setPositiveButton("OK", (dialog, which) -> {
            String playerName = input.getText().toString();
            saveScoreAndNameToDatabase(playerName, score);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
        builder.show();
    }

    // Method to save the score and name to the database
    private void saveScoreAndNameToDatabase(String name, int score) {
        HighScore highScore = new HighScore(name, score);

        new Thread(() -> {
            appDatabase.highScoreDao().insertHighScore(highScore);
        }).start();

        // Show a Snackbar to indicate that the score and name are saved
        showSnackbar("Score: " + score + " saved for " + name);
    }

    // Method to show a Snackbar
    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }

    // Helper method to load high scores from the Room database
    private void loadHighScoresFromDatabase() {
        // Use a new thread to fetch the top 10 high scores from the database
        new Thread(() -> {
            List<HighScore> topHighScores = appDatabase.highScoreDao().getTopHighScores();

            // Update the UI to display the top high scores
            runOnUiThread(() -> {
                showTopHighScores(topHighScores);
            });
        }).start();
    }
}
