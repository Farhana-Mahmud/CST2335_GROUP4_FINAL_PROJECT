package TriviaDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HighScoreDao {
    @Insert
    void insertHighScore(HighScore  highScore);


    @Query("SELECT * FROM high_scores ORDER BY score DESC LIMIT 10")
    List<HighScore> getTopHighScores();

    @Query("SELECT * FROM high_scores ORDER BY score DESC")
    List<HighScore> getAllHighScores();
}
