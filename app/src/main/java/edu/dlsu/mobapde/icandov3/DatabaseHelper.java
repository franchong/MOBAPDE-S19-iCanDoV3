package edu.dlsu.mobapde.icandov3;

/**
 * Created by CheskaLouise on 12/14/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by G301 on 11/7/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String SCHEMA = "icandov3";
    public static final int VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, SCHEMA, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // ROLE: create the tables for the schema
        // It will only be called once by the system
        // -- when the schema with given name doesn't exist yet

        // creates the task
        String sql = "CREATE TABLE " + Task.TABLE_NAME + " ("
                + Task.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Task.COLUMN_TITLE + " TEXT,"
                + Task.COLUMN_DESC + " TEXT,"
                + Task.COLUMN_DUEDATE + " DATE,"
                + Task.COLUMN_CREATIONDATE + " DATE,"
                + Task.COLUMN_CAT + "INTEGER"
                + Task.COLUMN_RECURR + "BOOLEAN"
                + ");";
        sqLiteDatabase.execSQL(sql);

        //creates Category
        sql = "CREATE TABLE " + Category.TABLE_NAME + " ("
                + Category.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Category.COLUMN_NAME + " TEXT,"
                + ");";
        sqLiteDatabase.execSQL(sql);

        //creates Rewards Table
        sql = "CREATE TABLE " + Reward.TABLE_NAME + " ("
                + Reward.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Reward.COLUMN_TITLE + " TEXT,"
                + Reward.COLUMN_DESC + " TEXT,"
                + Reward.COLUMN_PRICE + " INTEGER,"
                + Reward.COLUMN_REPEATABLE+ " BOOLEAN,"
                + ");";
        sqLiteDatabase.execSQL(sql);

        //TODO Add and clarify points system
        sql = "CREATE TABLE " + "GAME" + " ("
                + "POINTS" + " INTEGER,"
                + ");";
        sqLiteDatabase.execSQL(sql);

        //TODO These are samples
        addCategory(new Category(0, "Category"));
        addTask(new Task(
                0,
                "Task0",
                "description",
                new Date(1,1,1),
                new Date(1,1,1),
                0,
                true));
        addTask(new Task(
                1,
                "Task1",
                "description",
                new Date(1,1,1),
                new Date(1,1,1),
                0,
                true));


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,
                          int i, int i1) {
        // ROLE: update the current schema
        // Will be called when version number is newer/higher

        // migration
        // drop current tables
        String sql = "DROP TABLE IF EXISTS " + Task.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);

        sql = "DROP TABLE IF EXISTS " + Category.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);

        sql = "DROP TABLE IF EXISTS " + Reward.TABLE_NAME + ";";
        sqLiteDatabase.execSQL(sql);

        sql = "DROP TABLE IF EXISTS " + "GAME" + ";";
        sqLiteDatabase.execSQL(sql);

        // call onCreate
        onCreate(sqLiteDatabase);
    }

    // add Task
    public long addTask(Task task){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Task.COLUMN_TITLE, task.getTitle());
        contentValues.put(Task.COLUMN_DESC, task.getDescription());
        contentValues.put(Task.COLUMN_DUEDATE, String.valueOf(task.getDuedate()));
        contentValues.put(Task.COLUMN_CREATIONDATE, String.valueOf(task.getCreatedate()));
        contentValues.put(Task.COLUMN_CAT, task.getCategoryID());
        contentValues.put(Task.COLUMN_RECURR, task.isRecurr());

        long id = db.insert(Task.TABLE_NAME,
                null,
                contentValues);
        db.close();
        return id;
    }

    // add Category
    public long addCategory(Category category){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Category.COLUMN_NAME, category.getName());

        long id = db.insert(Category.TABLE_NAME,
                null,
                contentValues);
        db.close();
        return id;
    }


    public long addReward(Reward rewards){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Reward.COLUMN_TITLE, rewards.getTitle());
        contentValues.put(Reward.COLUMN_DESC, rewards.getDescription());
        contentValues.put(Reward.COLUMN_PRICE, rewards.getPrice());
        contentValues.put(Reward.COLUMN_RECURR, rewards.isRecurring());
        contentValues.put(Reward.COLUMN_REPEATABLE, rewards.isRepeatable());

        long id = db.insert(Category.TABLE_NAME,
                null,
                contentValues);
        db.close();
        return id;
    }

    //editTask
    public boolean editTask(Task newtask, long currentId){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Task.COLUMN_TITLE, newtask.getTitle());
        contentValues.put(Task.COLUMN_DESC, newtask.getDescription());
        contentValues.put(Task.COLUMN_DUEDATE, String.valueOf(newtask.getDuedate()));
        contentValues.put(Task.COLUMN_CREATIONDATE, String.valueOf(newtask.getCreatedate()));
        contentValues.put(Task.COLUMN_CAT, newtask.getCategoryID());
        contentValues.put(Task.COLUMN_RECURR, newtask.isRecurr());

        int rowsAffected = db.update(Task.TABLE_NAME,
                contentValues,
                Task.COLUMN_ID + "=?",
                new String[]{newtask.getId()+""});
        db.close();

        return rowsAffected >0;
    }
    //editCategory
    public boolean editCategory(Category category, int currentId){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Category.COLUMN_NAME, category.getName());

        int rowsAffected = db.update(Category.TABLE_NAME,
                contentValues,
                Category.COLUMN_ID + "=?",
                new String[]{category.getId()+""});
        db.close();

        return rowsAffected >0;
    }

    public boolean editReward(Reward rewards, int currentId){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Reward.COLUMN_TITLE, rewards.getTitle());
        contentValues.put(Reward.COLUMN_DESC, rewards.getDescription());
        contentValues.put(Reward.COLUMN_PRICE, rewards.getPrice());
        contentValues.put(Reward.COLUMN_RECURR, rewards.isRecurring());
        contentValues.put(Reward.COLUMN_REPEATABLE, rewards.isRepeatable());

        int rowsAffected = db.update(Reward.TABLE_NAME,
                contentValues,
                Reward.COLUMN_ID + "=?",
                new String[]{rewards.getId()+""});
        db.close();

        return rowsAffected >0;
    }

    // deleteTask
    public boolean deleteTask(long id){
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(Task.TABLE_NAME,
                Task.COLUMN_ID + "=?",
                new String[]{id+""} );
        db.close();
        return rowsAffected >0;
    }
    // deleteCategory
    public boolean deleteCategory(long id){
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(Category.TABLE_NAME,
                Category.COLUMN_ID + "=?",
                new String[]{id+""} );
        db.close();
        return rowsAffected >0;
    }
    // deleteReward
    public boolean deleteReward(long id){
        SQLiteDatabase db = getWritableDatabase();
        int rowsAffected = db.delete(Reward.TABLE_NAME,
                Reward.COLUMN_ID + "=?",
                new String[]{id+""} );
        db.close();
        return rowsAffected >0;
    }

    // getTask
    public Task getTask(long id){
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy");
        Cursor c = db.query(Task.TABLE_NAME,
                null,
                Task.COLUMN_ID + "=?",
                new String[]{ id+"" },
                null,
                null,
                null);
        Task t = null;
        if(c.moveToFirst()){
            t = new Task();
            t.setTitle(c.getString(c.getColumnIndex(Task.COLUMN_TITLE)));
            t.setDescription(c.getString(c.getColumnIndex(Task.COLUMN_DESC)));
            String date = c.getString(c.getColumnIndex(Task.COLUMN_DUEDATE));
            Date dd = null;
            try {
                dd = df.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t.setDuedate(dd);

            String credate = c.getString(c.getColumnIndex(Task.COLUMN_CREATIONDATE));
            Date cd = null;
            try {
                cd = df.parse(credate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            t.setCreatedate(cd);
            t.setRecurr(c.getInt(c.getColumnIndex(Task.COLUMN_RECURR)) > 0);
            t.setCategoryID(c.getLong(c.getColumnIndex(Task.COLUMN_CAT)));
            t.setId(id);
        }

        c.close();
        db.close();

        return t;
    }
    // getCategory
    public Category getCategory(long id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Category.TABLE_NAME,
                null,
                Category.COLUMN_ID + "=?",
                new String[]{ id+"" },
                null,
                null,
                null);
        Category k = null;
        if(c.moveToFirst()){
            k = new Category();
            k.setName(c.getString(c.getColumnIndex(Category.COLUMN_NAME)));
            k.setId(id);
        }

        c.close();
        db.close();

        return k;
    }
    // getReward
    public Reward getReward(long id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.query(Reward.TABLE_NAME,
                null,
                Reward.COLUMN_ID + "=?",
                new String[]{ id+"" },
                null,
                null,
                null);
        Reward t = null;
        if(c.moveToFirst()){
            t = new Reward();
            t.setTitle(c.getString(c.getColumnIndex(Reward.COLUMN_TITLE)));
            t.setDescription(c.getString(c.getColumnIndex(Reward.COLUMN_DESC)));
            t.setPrice(c.getInt(c.getColumnIndex(Reward.COLUMN_PRICE)));
            // t.setRepeatable(c.get(c.getColumnIndex(Reward.COLUMN_REPEATABLE)));
            //t.setRecurring(c.getBoolean(c.getColumnIndex(Reward.COLUMN_RECURR)));
            t.setId(id);
        }

        c.close();
        db.close();

        return t;
    }

    // getAllTasks
    public Cursor getAllTasksCursor(String PRIORITY, String SORT){
        return getReadableDatabase().query(Task.TABLE_NAME, null,null,null,null,null,PRIORITY+" " +SORT);
    }

    // getAllCategories
    public Cursor getAllCategoriesCursor(String PRIORITY, String SORT){
        return getReadableDatabase().query(Category.TABLE_NAME, null,null,null,null,null,PRIORITY+" " +SORT);
    }

    // getAllReward
    public Cursor getAllRewardCursor(String PRIORITY, String SORT){
        return getReadableDatabase().query(Reward.TABLE_NAME, null,null,null,null,null,PRIORITY +" " +SORT);
    }

    public Cursor getAllTasksCursor(){
        return getReadableDatabase().query(Task.TABLE_NAME, null,null,null,null,null,null);
    }

    public Cursor getAllCategoriesCursor(){
        return getReadableDatabase().query(Category.TABLE_NAME, null,null,null,null,null,null);
    }

    public Cursor getAllRewardCursor(){
        return getReadableDatabase().query(Reward.TABLE_NAME, null,null,null,null,null,null);
    }
}