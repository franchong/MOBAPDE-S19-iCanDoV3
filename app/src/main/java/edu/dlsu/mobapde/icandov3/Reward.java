package edu.dlsu.mobapde.icandov3;

/**
 * Created by Dell on 12/09/2017.
 */

public class Reward {

    private int icon;
    String title;
    String description;
    int points;

    public Reward(int icon, String title, String description, int points) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.points = points;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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

}
