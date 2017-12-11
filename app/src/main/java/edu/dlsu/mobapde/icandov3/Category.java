package edu.dlsu.mobapde.icandov3;

/**
 * Created by Dell on 12/08/2017.
 */

public class Category {

    private int icon;
    private String title;
    private int numOfTasks;


    public Category(int icon, String title, int numOfTasks) {
        this.icon = icon;
        this.title = title;
        this.numOfTasks = numOfTasks;
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

    public int getNumOfTasks() {
        return numOfTasks;
    }

    public void setNumOfTasks(int numOfTasks) {
        this.numOfTasks = numOfTasks;
    }

}
