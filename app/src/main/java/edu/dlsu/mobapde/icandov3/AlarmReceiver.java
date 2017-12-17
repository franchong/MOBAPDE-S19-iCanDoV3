package edu.dlsu.mobapde.icandov3;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by saniuri on 16/12/2017.
 */

public class AlarmReceiver extends BroadcastReceiver{

    DatabaseHelper db;
    long id;
    String title;
    String desc;
    Date dueDate;
    String strDueDate;
    Date createDate;
    String strCreateDate;
    long cat;
    boolean recurr;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("ALARMRECEIVER", "EXECUTED");
        db = new DatabaseHelper(context);

        Cursor cursor = db.getAllTasksCursor();
        ArrayList<Task> taskArrayList = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                id = cursor.getInt(cursor.getColumnIndex(Task.COLUMN_ID));
                title = cursor.getString(cursor.getColumnIndex(Task.COLUMN_ID));
                desc = cursor.getString(cursor.getColumnIndex(Task.COLUMN_ID));
                strDueDate = cursor.getString(cursor.getColumnIndex(Task.COLUMN_ID));
                strCreateDate = cursor.getString(cursor.getColumnIndex(Task.COLUMN_ID));
                cat = cursor.getLong(cursor.getColumnIndex(Task.COLUMN_ID));

                Task t = new Task(title, desc, strDueDate, strCreateDate, cat, false);
                taskArrayList.add(t);
            }
        } finally {
            cursor.close();
        }

        ArrayList<Task> tasksDueToday = new ArrayList<>();
        ArrayList<Task> tasksOverDue = new ArrayList<>();
        //TODO overdue reminder

        Date today = Calendar.getInstance().getTime();

        for (Task t : taskArrayList) {
            SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy");
            try {
                Date duedate = df.parse(t.getDuedate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (compareTwoDates(today, dueDate) == 0) {
                tasksDueToday.add(t);
            }
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);

        Intent saIntent = new Intent(context, TaskListActivity.class);
        PendingIntent saPI = PendingIntent.getActivity(context, 0, saIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        String strDueTasks = "";
        for (Task t : tasksDueToday) {
            strDueTasks = strDueTasks + t.getTitle() + ", ";
        }

            NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                    .setContentIntent(saPI)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setTicker(tasksDueToday.size() + " Tasks Due Today")
                    .setContentTitle(tasksDueToday.size() + " Tasks Due Today")
                    .setContentText(strDueTasks);

        notificationManager.notify(0, builder.build());
    }

    public static int compareTwoDates(Date startDate, Date endDate) {
        /**
         * return 1 if task is not overdue
         * return -1 if task is overdue
         * return 0 if task is due today
         *
         */
        Date sDate = getZeroTimeDate(startDate);
        Date eDate = getZeroTimeDate(endDate);
        if (sDate.before(eDate)) {
            return 1;
        }
        if (sDate.after(eDate)) {
            return -1;
        }
        return 0;
    }

    private static Date getZeroTimeDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        //calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        return date;
    }
}
