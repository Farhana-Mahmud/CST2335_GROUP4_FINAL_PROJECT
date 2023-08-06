package TriviaDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.example.flighttracker.R;

import java.util.ArrayList;


/**
 *
 *
 * A class to attempt the class
 * @author afnan
 */
public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ArrayList<TriviaQuestion> triviaQuestionList = getIntent().getParcelableArrayListExtra("questionList");

        // Find the RecyclerView by its ID
        RecyclerView recyclerView = findViewById(R.id.recyclerViewQuiz);

        // Create a LinearLayoutManager for the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // Create the QuestionAdapter with the parsed questions and set it to the RecyclerView
        QuestionAdapter adapter = new QuestionAdapter(triviaQuestionList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }


}
