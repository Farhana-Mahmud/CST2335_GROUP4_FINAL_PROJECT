package TriviaDatabase;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "high_scores")
public class HighScore implements Parcelable {
    @ColumnInfo(name = "name")
    private String playerName;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "score")
    private int score;

    public HighScore(String playerName, int score) {
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setScore(int score) {
        this.score = score;
    }




    // Parcelable implementation
    protected HighScore(Parcel in) {
        playerName = in.readString();
        score = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(playerName);
        dest.writeInt(score);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<HighScore> CREATOR = new Creator<HighScore>() {
        @Override
        public HighScore createFromParcel(Parcel in) {
            return new HighScore(in);
        }

        @Override
        public HighScore[] newArray(int size) {
            return new HighScore[size];
        }
    };









}
