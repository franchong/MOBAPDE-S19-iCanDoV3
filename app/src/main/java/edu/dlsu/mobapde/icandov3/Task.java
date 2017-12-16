package edu.dlsu.mobapde.icandov3;

import java.util.Date;

/**
 * Created by Dell on 12/08/2017.
 */

public class Task {

    public static final String TABLE_NAME = "task";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC = "desc";
    public static final String COLUMN_DUEDATE = "duedate";
    public static final String COLUMN_CREATIONDATE = "createdate";
    public static final String COLUMN_RECURR = "recurr";
    public static final String COLUMN_CAT = "categoryID";

    private int icon;
    private long id;
    private String title;
    private String description;
    private Date duedate;
    private Date createdate;
    private long categoryID;
    public boolean isRecurr;

    public Task () {}
    public Task(String title, String description, Date duedate, Date createdate, long categoryID, boolean isRecurr) {
        this.title = title;
        this.description = description;
        this.duedate = duedate;
        this.createdate = createdate;
        this.categoryID = categoryID;
        this.isRecurr = isRecurr;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
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

    public Date getDuedate() {
        return duedate;
    }

    public void setDuedate(Date duedate) {
        this.duedate = duedate;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    public long getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(long categoryID)
    {
        this.categoryID = categoryID;
    }

    public boolean isRecurr() {
        return isRecurr;
    }

    public void setRecurr(boolean recurr) {
        isRecurr = recurr;
    }




}