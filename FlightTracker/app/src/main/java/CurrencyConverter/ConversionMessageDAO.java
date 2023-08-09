package CurrencyConverter;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


/**
 * Data Access Object (DAO) interface for interacting with the database table containing chat messages.
 * This interface defines methods to insert, query, and delete chat messages from the database.
 */
@Dao
public interface ConversionMessageDAO {
    /**
     * Inserts a new chat message into the database.
     *
     * @param m The chat message to be inserted.
     * @return The auto-generated ID assigned to the newly inserted message.
     */
    @Insert
    public long insertMessage(ChatMessage m);
    /**
     * Retrieves all chat messages from the database.
     *
     * @return A list of all chat messages stored in the database.
     */
    @Query("Select * from ChatMessage")
    public List<ChatMessage> getAllMessages();
    /**
     * Deletes a chat message from the database.
     *
     * @param m The chat message to be deleted.
     */
    @Delete
    public void deleteMessage(ChatMessage m);

}
