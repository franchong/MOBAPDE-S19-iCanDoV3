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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by saniuri on 16/12/2017.
 */

public class AlarmReceiver extends BroadcastReceiver{

    DatabaseHelper db;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("ALARMRECEIVER", "EXECUTED");
        db = new DatabaseHelper(context);

        Cursor cursor = db.getAllTasksCursor();
        int tasksDueToday = 0;
        Date today = Calendar.getInstance().getTime();
        String strDueTasks = "";

        try {
            while (cursor.moveToNext()) {
                // Note: if you store it in a variable, the Strings become numbers
//                title = cursor.getString(cursor.getColumnIndex(Task.COLUMN_ID));
                //Log.i("LOG DATE", "Title: " + title + " DueDate: " + strDueDate);

//                Log.i("LOG CURSOR TITLE", cursor.getString(cursor.getColumnIndex(Task.COLUMN_TITLE)));
//                Log.i("LOG CURSOR DATE", cursor.getString(cursor.getColumnIndex(Task.COLUMN_DUEDATE)));

                SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
                String strToday = df.format(today);
//                Log.i("LOG TODAY", strToday);

                if (strToday.equals(cursor.getString(cursor.getColumnIndex(Task.COLUMN_DUEDATE)))) {
                    strDueTasks = strDueTasks + cursor.getString(cursor.getColumnIndex(Task.COLUMN_TITLE)) + ", ";
                    tasksDueToday++;
                }
            }
        } finally {
            cursor.close();
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Service.NOTIFICATION_SERVICE);

        Intent saIntent = new Intent(context, TaskListActivity.class);
        PendingIntent saPI = PendingIntent.getActivity(context, 0, saIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setContentIntent(saPI)
                .setSmallIcon(R.drawable.check_circle_white)
                .setTicker(tasksDueToday+ " Tasks Due Today : " + strDueTasks)
                .setContentTitle(tasksDueToday + " Tasks Due Today")
                .setContentText(strDueTasks);

        notificationManager.notify(0, builder.build());
    }

}
