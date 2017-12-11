package edu.dlsu.mobapde.icandov3;

/**
 * Created by Dell on 12/08/2017.
 */

public class Task {

    private int icon;
    private String title;
    private String description;
    private String dueDate;
    private String day;
    private int daysLeft;
    private boolean recurring;

    public Task(int icon, String title, String description, String dueDate, String day, int daysLeft, boolean recurring) {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.day = day;
        this.daysLeft = daysLeft;
        this.recurring = recurring;
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

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }

    public boolean isRecurring() {
        return recurring;
    }

    public void setRecurring(boolean recurring) {
        this.recurring = recurring;
    }
}
