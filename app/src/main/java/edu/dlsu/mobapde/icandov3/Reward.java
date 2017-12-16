package edu.dlsu.mobapde.icandov3;

/**
 * Created by Dell on 12/09/2017.
 */

public class Reward {
    public static final String TABLE_NAME = "reward";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_POINTS = "points";
    public static final String COLUMN_REPEATABLE= "repeatable";

    private long id;
    private String title;
    private String description;
    private int points;
    private boolean isRepeatable;

    public Reward(){}

    public Reward(String title, String description, int points, boolean isRepeatable) {
        this.title = title;
        this.description = description;
        this.points = points;
        this.isRepeatable = isRepeatable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public boolean isRepeatable() {
        return isRepeatable;
    }

    public void setRepeatable(boolean repeatable) {
        isRepeatable = repeatable;
    }

}
