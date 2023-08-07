package algonquin.cst2335.kaur0943;
/**
 * Represents a chat message entity to be stored in the database.
 * Each ChatMessage object corresponds to a row in the database table.
 */
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage{

    @PrimaryKey(autoGenerate=true)
    @ColumnInfo(name="id")
    public int id;

    @ColumnInfo(name="message")
    public String message;

    @ColumnInfo(name="timeSent")
    public String timeSent;

    @ColumnInfo(name="isSentButton")
    public boolean isSentButton;
    /**
     * Default constructor for the ChatMessage class.
     * Creates an empty ChatMessage object with default values.
     */

    public ChatMessage() {
        // Default constructor
    }
    /**
     * Constructor for the ChatMessage class.
     *
     * @param m The message content of the chat message.
     * @param t The timestamp when the message was sent.
     * @param sent A boolean indicating whether the message is a sent button or not.
     */
    public ChatMessage(String m, String t, boolean sent)
    {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }
    /**
     * Gets the message content of the chat message.
     *
     * @return The message content as a String.
     */
    public String getMessage() {
        return message;
    }
    /**
     * Gets the timestamp when the message was sent.
     *
     * @return The timestamp as a String.
     */
    public String getTimeSent() {
        return timeSent;
    }
    /**
     * Checks if the chat message represents a sent button or a regular message.
     *
     * @return {@code true} if the message is a sent button, {@code false} otherwise.
     */
    public boolean isSentButton() {
        return isSentButton;
    }
}
