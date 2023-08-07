package algonquin.cst2335.kaur0943.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import algonquin.cst2335.kaur0943.ChatMessage;
/**
 * A Room Database class that serves as the database for storing chat messages.
 * This class is responsible for defining the database configuration and providing access to the Data Access Object (DAO) for chat message operations.
 */
@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    /**
     * Gets the Data Access Object (DAO) for chat message operations.
     *
     * @return The Data Access Object (DAO) for chat message operations.
     */
    public abstract ConversionMessageDAO cmDAO();
}
