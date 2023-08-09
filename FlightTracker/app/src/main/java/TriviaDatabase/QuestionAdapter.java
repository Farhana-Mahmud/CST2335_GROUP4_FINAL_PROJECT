package TriviaDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flighttracker.R;

import java.util.ArrayList;


/**
 * A question adapter class to show questions through scrolling
 * @author afnan
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> {

    private ArrayList<TriviaQuestion> triviaQuestions;


    /**
     *
     * @param triviaQuestions show the list of questions
     */
    public QuestionAdapter(ArrayList<TriviaQuestion> triviaQuestions) {
        this.triviaQuestions = triviaQuestions;
    }

    /**
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return  QuestionViewHolder
     */
    @NonNull
    @Override
    public QuestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        return new QuestionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionViewHolder holder, int position) {
        TriviaQuestion question = triviaQuestions.get(position);
        holder.bind(question);
    }


    /**
     *
     * @return it returns the number of questions that user entered
     */
    @Override
    public int getItemCount() {
        return triviaQuestions.size();
    }

    /**
     * a class to shows question
     */
    class QuestionViewHolder extends RecyclerView.ViewHolder {
        /**
         * an attribute for questionTextView
         */
        private TextView questionTextView;

        /**
         *  an attribute for options
          */
        private RadioGroup optionsRadioGroup;



        /**
         *
          * @param itemView shows item
         */
        QuestionViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTextView = itemView.findViewById(R.id.questionTextView);
            optionsRadioGroup = itemView.findViewById(R.id.optionsRadioGroup);
        }

        void bind(TriviaQuestion question) {
            questionTextView.setText(question.getQuestion());

            optionsRadioGroup.removeAllViews(); // Clear previous options

            // Add options RadioButtons programmatically
            for (String option : question.getOptions()) {
                RadioButton radioButton = new RadioButton(itemView.getContext());
                radioButton.setText(option);
                optionsRadioGroup.addView(radioButton);
            }
        }
    }
}
