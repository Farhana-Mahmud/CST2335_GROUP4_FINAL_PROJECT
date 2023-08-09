package TriviaDatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.flighttracker.R;

/**
 *
 * this class shows the category of topics you can choose from
 * @author afnan
 */
public class MainActivity2 extends AppCompatActivity {
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
        setContentView(R.layout.activity_main2);

        // Find the button by its ID
        Button button = findViewById(R.id.button);

        //OnClickListener for the button
        button.setOnClickListener(v -> {
            // AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
            builder.setTitle("Alert")
                    .setMessage("Select One Category to Use")
                    .setPositiveButton("OK", null)
                    .show();
        });

        // CardViews for the categories
        CardView geographyCard = findViewById(R.id.geographyCard);
        CardView entertainmentCard = findViewById(R.id.entertainmentCard);
        CardView scienceCard = findViewById(R.id.scienceCard);
        CardView generalKnowledgeCard = findViewById(R.id.generalKnowledgeCard);

        //  click listeners for each category CardView
        geographyCard.setOnClickListener(v -> openSecondActivity("Geography"));
        entertainmentCard.setOnClickListener(v -> openSecondActivity("Entertainment"));
        scienceCard.setOnClickListener(v -> openSecondActivity("Science"));
        generalKnowledgeCard.setOnClickListener(v -> openSecondActivity("General Knowledge"));
    }

    private void openSecondActivity(String category) {
        Intent intent = new Intent(MainActivity2.this, SecondActivity.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}
