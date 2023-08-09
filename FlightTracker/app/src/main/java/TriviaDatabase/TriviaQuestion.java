package TriviaDatabase;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * This class calls the data from trivia website
 * @author afnan
 */
public class TriviaQuestion implements Parcelable {
    private String question;
    private String correctAnswer;
    private ArrayList<String> incorrectAnswers;
    private String selectedAnswer;

    /**
     *
     * @return selectedAnswer
     */
    public String getSelectedAnswer() {
        return selectedAnswer;
    }


    /**
     *
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     *
     * @return correctAnswer
     */

    public String getCorrectAnswer() {
        return correctAnswer;
    }


    /**
     * Add the getOptions method to return all options for the question
     * @return options
     */
    public ArrayList<String> getOptions() {
        ArrayList<String> options = new ArrayList<>();
        options.add(correctAnswer);
        options.addAll(incorrectAnswers);
        return options;
    }


    /**
     *
     * @param question  shows the question from the user
     * @param correctAnswer shows the correct Answer from the user
     * @param incorrectAnswers shows the incorrect Answers from the user
     */
    public TriviaQuestion(String question, String correctAnswer, ArrayList<String> incorrectAnswers) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.incorrectAnswers = incorrectAnswers;
    }

    /**
     * Parcelable implementation methods
     * @param in  read the corrected answer or incorrect answer
     */
    protected TriviaQuestion(Parcel in) {
        question = in.readString();
        correctAnswer = in.readString();
        incorrectAnswers = in.createStringArrayList();
    }


    /**
     *
     */
    public static final Creator<TriviaQuestion> CREATOR = new Creator<TriviaQuestion>() {
        @Override
        public TriviaQuestion createFromParcel(Parcel in) {
            return new TriviaQuestion(in);
        }

        @Override
        public TriviaQuestion[] newArray(int size) {
            return new TriviaQuestion[size];
        }
    };

    /**
     *
     * @return zero
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     *
     * @param dest The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     * May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(correctAnswer);
        dest.writeStringList(incorrectAnswers);
    }



}
