package TriviaDatabase;
import com.example.flighttracker.R;
import com.google.android.material.snackbar.Snackbar;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * A class to enter the name of user
 * @author afnan
 */
public class NameEntryActivity extends AppCompatActivity {

    private AppDatabase appDatabase; // Room database instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_entry);

        // Initialize the Room database instance
        appDatabase = AppDatabase.getInstance(this);

        // Retrieve the score from the extras
        int score = getIntent().getIntExtra("score", 0);

        // Find the views by their IDs
        EditText editTextName = findViewById(R.id.editTextName);
        Button buttonSaveName = findViewById(R.id.buttonSaveName);
        Button buttonShowHighScores = findViewById(R.id.buttonShowHighScores);


        buttonShowHighScores.setOnClickListener(v -> {
            // Navigate to the HighScoreActivity to show the high scores
            Intent intent = new Intent(NameEntryActivity.this, HighScoreActivity.class);

            // Get the list of high scores from the database and pass it to the HighScoreActivity
            List<HighScore> highScoresList = appDatabase.highScoreDao().getAllHighScores();
            intent.putParcelableArrayListExtra("highScoresList", new ArrayList<>(highScoresList));

            startActivity(intent);
        });


        // Set click listeners for the buttons
        buttonSaveName.setOnClickListener(v -> {
            // Get the user's name from the EditText
            String name = editTextName.getText().toString().trim();

            // Save the score and name to the database
            saveScoreAndNameToDatabase(name, score);

            // Show a Snackbar to indicate that the score and name are saved
            showSnackbar("Score: " + score + " saved for " + name);

        });

        buttonShowHighScores.setOnClickListener(v -> {
            // Navigate to the HighScoreActivity to show the high scores
            Intent intent = new Intent(NameEntryActivity.this, HighScoreActivity.class);
            startActivity(intent);
        });
    }

    // Method to show a Snackbar
    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }


    // Method to show an AlertDialog and save score and name to the database
    private void showNameDialog(int score) {
        // Create an EditText view to get the user's name
        EditText editTextName = new EditText(this);
        editTextName.setHint("Enter your name");

        // Create an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Your Name")
                .setView(editTextName)
                .setPositiveButton("Submit", (dialog, which) -> {
                    // Get the user's name from the EditText
                    String name = editTextName.getText().toString().trim();

                    // Save the score and name to the database
                    saveScoreAndNameToDatabase(name, score);

                    // Now, you can display the high scores or perform any other action.
                    // For example, navigate to a HighScoresActivity to show the top 10 high scores.
                    Intent intent = new Intent(NameEntryActivity.this, HighScoreActivity.class);
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    // Method to save the score and name to the database using Room
    private void saveScoreAndNameToDatabase(String name, int score) {
        // Create a new HighScore object with the name and score
        HighScore highScore = new HighScore(name, score);

        // Save the high score to the database using the DAO
        new Thread(() -> {
            appDatabase.highScoreDao().insertHighScore(highScore);
        }).start();
    }



}
