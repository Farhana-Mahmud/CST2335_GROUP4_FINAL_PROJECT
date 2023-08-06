package TriviaDatabase;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.flighttracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * This class shows the question for each category when user select the question number
 * by connecting API and downloading it
 * @author afnan
 */
public class SecondActivity extends AppCompatActivity {

    // Define a constant key for shared preferences
    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_NUM_OF_QUESTIONS = "NumOfQuestions";
    private static final String KEY_SELECTED_TOPIC = "SelectedTopic";
    private ArrayList<TriviaQuestion> triviaQuestionList;
    private RecyclerView recyclerView;


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
        setContentView(R.layout.activity_second);





        // Retrieve the EditText view for number of questions
        EditText editTextNumberOfQuestions = findViewById(R.id.editTextNumberOfQuestions);

        // Get the saved value from shared preferences and set it as the text for the EditText
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedNumOfQuestions = sharedPreferences.getString(KEY_NUM_OF_QUESTIONS, "");
        editTextNumberOfQuestions.setText(savedNumOfQuestions);

        // Create a RecyclerView instance
        recyclerView = findViewById(R.id.recyclerView);

        // Create a LinearLayoutManager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Initialize the questionList
        triviaQuestionList = new ArrayList<>();

        // Find the button by its ID
        Button button = findViewById(R.id.button2);

        // Set an OnClickListener for the button
        button.setOnClickListener(v -> {
            // Create an AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
            builder.setTitle("Alert")
                    .setMessage("Add Number of questions to Download(Max 50!)")
                    .setPositiveButton("OK", null)
                    .show();
        });

        // Find the buttonDownload and set an OnClickListener
        Button buttonDownload = findViewById(R.id.buttonDownload);


        buttonDownload.setOnClickListener(v -> {
            // Retrieve the value from the EditText
            String numOfQuestions = editTextNumberOfQuestions.getText().toString().trim();
            String selectedTopic = "YourSelectedTopic"; // Update this with the actual selected topic from your UI (e.g., spinner, radio button, etc.)

            // Check if the number of questions is valid
            if (TextUtils.isEmpty(numOfQuestions)) {
                Toast.makeText(this, "Please enter the number of questions", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int numberOfQuestions = Integer.parseInt(numOfQuestions);
                if (numberOfQuestions <= 0 || numberOfQuestions > 50) {
                    Toast.makeText(this, "Number of questions should be between 1 and 50", Toast.LENGTH_SHORT).show();
                } else {
                    // Save the data to SharedPreferences
                    saveData(selectedTopic, numOfQuestions);

                    // Download questions from the API
                    downloadQuestions(numOfQuestions);
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Please enter a valid number for the number of questions", Toast.LENGTH_SHORT).show();
            }




        });



// Find the buttonSubmit and set an OnClickListener
        Button buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(v -> {
            // Compute the score
            int score = computeScore();

            // Navigate to the NameEntryActivity and pass the score as an extra
            Intent intent = new Intent(SecondActivity.this, NameEntryActivity.class);
            intent.putExtra("score", score);
            startActivity(intent);


        });



    }




    private int computeScore() {
        int score = 0;

        // Iterate through the triviaQuestionList and check each question's answer
        for (TriviaQuestion question : triviaQuestionList) {
            // Check if the selected option matches the correct answer
            if (question.getSelectedAnswer() != null && question.getSelectedAnswer().equals(question.getCorrectAnswer())) {
                // Increase the score if the answer is correct
                score++;
            }
        }

        // Return the computed score
        return score;
    }





    private void saveData(String topic, String numOfQuestions) {
        // Save the data to SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NUM_OF_QUESTIONS, numOfQuestions);
        editor.putString(KEY_SELECTED_TOPIC, topic); // Add the selected topic to SharedPreferences
        editor.apply();

        //  Toast to indicate that data has been saved
        Toast.makeText(this, "Selected topic: " + topic + ", Number of questions: " + numOfQuestions, Toast.LENGTH_SHORT).show();
    }
    private int getCategoryCode(String category) {
        // Implement a method to map category names to category codes
        // For example, assuming the available categories and their codes are as follows:
        switch (category) {
            case "Geography":
                return 22;
            case "Entertainment":
                return 11;
            case "Science":
                return 17;
            case "General Knowledge":
                return 9;
            default:
                // If the category is not recognized, return a default category code (e.g., 9 for General Knowledge)
                return 9;
        }
    }






    private void downloadQuestions(String numOfQuestions) {
        int numberOfQuestions = Integer.parseInt(numOfQuestions);

        // Retrieve the selected category from Intent extras
        String selectedCategory = getIntent().getStringExtra("category");

        // Construct the URL for the API request with the selected category
        String apiUrl = "https://opentdb.com/api.php?amount=" + numberOfQuestions + "&category=" + getCategoryCode(selectedCategory) + "&type=multiple";
        // Create a JsonObjectRequest using Volley
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Parse the JSON response and extract questions
                        List<TriviaQuestion> questions = null;
                        try {
                            questions = parseJsonResponse(response);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // Clear the questionList
                        triviaQuestionList.clear();

                        // Add the downloaded questions to the questionList
                        triviaQuestionList.addAll(questions);

                        // After downloading questions, set up the RecyclerView Adapter and display the questions
                        QuestionAdapter adapter = new QuestionAdapter(triviaQuestionList);
                        recyclerView.setAdapter(adapter);
                    }
                },
                new
                        Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(SecondActivity.this, "Failed to fetch questions from API", Toast.LENGTH_SHORT).show();
                    }
                });

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }

    /**
     * Helper method to parse JSON response and create a list of TriviaQuestion objects
     * @param response
     * @return
     * @throws JSONException
     */
    private List<TriviaQuestion> parseJsonResponse(JSONObject response) throws JSONException {
        List<TriviaQuestion> questionList = new ArrayList<>();

        try {
            // Check if the response contains the "results" array
            if (response.has("results")) {
                JSONArray resultsArray = response.getJSONArray("results");

                for (int i = 0; i < resultsArray.length(); i++) {
                    JSONObject result = resultsArray.getJSONObject(i);

                    String question = result.optString("question", "");
                    String correctAnswer = result.optString("correct_answer", "");
                    JSONArray incorrectAnswersArray = result.getJSONArray("incorrect_answers");
                    List<String> incorrectAnswers = new ArrayList<>();

                    // Add incorrect answers to the list
                    for (int j = 0; j < incorrectAnswersArray.length(); j++) {
                        String incorrectAnswer = incorrectAnswersArray.optString(j, "");
                        incorrectAnswers.add(incorrectAnswer);
                    }


                    // Create a TriviaQuestion object and add it to the list
                    TriviaQuestion triviaQuestion = new TriviaQuestion(question, correctAnswer, (ArrayList<String>) incorrectAnswers);
                    questionList.add(triviaQuestion);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return questionList;
    } }
