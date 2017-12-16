package edu.dlsu.mobapde.icandov3;

/**
 * Created by CheskaLouise on 12/15/2017.
 */

public class User {

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_POINTS = "points";

    private int level;
    private long points;

    public User(int level, long points) {
        this.level = level;
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
}
