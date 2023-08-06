package TriviaDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


/**
 * @author afnan
 */
@Database(entities = {HighScore.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "app_database";

    private static AppDatabase instance;

    /**
     *
     * @param context shows the instance for database
     * @return instance
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .build();
        }
        return instance;
    }

    /**
     *
     * @return HighScoreDao
     */
    public abstract HighScoreDao highScoreDao();
}
