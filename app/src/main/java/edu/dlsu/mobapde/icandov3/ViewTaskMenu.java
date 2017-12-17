package edu.dlsu.mobapde.icandov3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ViewTaskMenu extends DialogFragment {

    ImageView ivRecurr;
    TextView tvTaskName, tvDaysLeft, tvDuedate, tvDay, tvDescription;
    long categoryID;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        final View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_view_task_menu, null);

        final DatabaseHelper db = new DatabaseHelper(v.getContext());

        tvTaskName = (TextView) v.findViewById(R.id.tv_taskname);
        tvDescription = (TextView) v.findViewById(R.id.tv_description);
        ivRecurr = (ImageView) v.findViewById(R.id.ivRecurr);
        tvDaysLeft = (TextView) v.findViewById(R.id.tv_daysleft);
        tvDuedate = (TextView) v.findViewById(R.id.tv_duedate);
        tvDay = (TextView) v.findViewById(R.id.tv_day);

        Bundle bundle = this.getArguments();
        final long id = bundle.getLong("id_");

        Task task = db.getTask(id);

        final String title = task.getTitle();
        final String description = task.getDescription();
        final String strDuedate = task.getDuedate();
        final String strCreatedate = task.getCreatedate();
        final long categoryID = task.getCategoryID();
        final boolean isRecurr = task.isRecurr();

        //Log.i("LOG duedate", strDuedate);

        tvTaskName.setText(title);
        tvDescription.setText(description);

        if (!isRecurr) {
            ivRecurr.setImageResource(R.drawable.nonrecurring);
        }
        else if (isRecurr) {
            ivRecurr.setImageResource(R.drawable.recurring);
        }

        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");

        Date endDateValue = null;
        try {
            endDateValue = df.parse(strDuedate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date today = Calendar.getInstance().getTime();
        Date startDateValue = today;
        String strToday = df.format(today);

        long diff = endDateValue.getTime() - startDateValue.getTime();
        long daysLeft = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;

        if (strDuedate.equals(strToday)) {
            daysLeft = 0;
        }

        //Log.i("LOG daysleft", Long.toString(daysLeft));

        if (daysLeft == 0) {
            tvDaysLeft.setText("Today");
        }
        else if (daysLeft == 1) {
            tvDaysLeft.setText("Tomorrow");
        }
        else {
            tvDaysLeft.setText(Long.toString(daysLeft) + " days from now");
        }

        tvDuedate.setText(strDuedate);

        String dayOfWeek = sdf.format(endDateValue);
        tvDay.setText(dayOfWeek);

        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("Complete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (isRecurr) {
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(Calendar.getInstance().getTime());
                            cal.add(Calendar.DAY_OF_YEAR, 1);
                            Date dueDate = cal.getTime();
                            Task t = new Task(title, description, dueDate.toString(), Calendar.getInstance().getTime().toString(),
                                    categoryID, isRecurr);
                            db.addTask(t);
                            db.deleteTask(id);
                        }
                        else {
                            db.deleteTask(id);
                        }

                        //TODO User Points
                        int currentPoints, currentLevel;

                        //TODO made V as final
                        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                        SharedPreferences.Editor dspEditor = dsp.edit();

                        currentPoints = dsp.getInt(User.COLUMN_POINTS, -1);
                        currentLevel = dsp.getInt(User.COLUMN_LEVEL, -1);
                        currentPoints += 5;
                        if (currentPoints == 100) {
                            currentLevel++;
                            dspEditor.putInt(User.COLUMN_LEVEL, currentLevel);
                            currentPoints = 0;
                        }
                        dspEditor.putInt(User.COLUMN_POINTS, currentPoints);
                        dspEditor.apply();

                        Log.i("LOG actual points", String.valueOf(dsp.getInt(User.COLUMN_POINTS, -1)));
                        Log.i("LOG actual level", String.valueOf(dsp.getInt(User.COLUMN_LEVEL, -1)));

                        ((TaskListActivity)getActivity()).update();

                        dismiss();
                    }
                })
                .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setClass(getActivity(), AddEditTaskMenu.class);
                        intent.putExtra("isEdit", true);
                        intent.putExtra(Task.COLUMN_ID, id);
                        intent.putExtra(Task.COLUMN_TITLE, title);
                        intent.putExtra(Task.COLUMN_DESC, description);
                        intent.putExtra(Task.COLUMN_DUEDATE, strDuedate);
                        intent.putExtra(Task.COLUMN_CREATIONDATE, strCreatedate);
                        intent.putExtra(Task.COLUMN_CAT, categoryID);
                        intent.putExtra(Task.COLUMN_RECURR, isRecurr);
                        startActivityForResult(intent, 8);
                        //TODO concern for activities transition, might need onActivityResult
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        return alertDialog;
    }
}

