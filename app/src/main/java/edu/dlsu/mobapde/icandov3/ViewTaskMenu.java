package edu.dlsu.mobapde.icandov3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_view_task_menu, null);

        final DatabaseHelper db = new DatabaseHelper(v.getContext());


        tvTaskName = (TextView) v.findViewById(R.id.tv_taskname);
        tvDaysLeft = (TextView) v.findViewById(R.id.tv_daysleft);
        tvDay = (TextView) v.findViewById(R.id.tv_day);
        tvDuedate = (TextView) v.findViewById(R.id.tv_duedate);
        tvDescription = (TextView) v.findViewById(R.id.tv_description);
        ivRecurr = (ImageView) v.findViewById(R.id.ivRecurr) ;

        Bundle bundle = this.getArguments();
        final long id = bundle.getLong("id_");

        Task task = db.getTask(id);
        //if (bundle != null) {
        final String title = task.getTitle();
        final String description = task.getDescription();
        final String strDuedate = task.getDuedate();
        final String strCreatedate = task.getCreatedate();
        final long categoryID = task.getCategoryID();
        final boolean isRecurr = task.isRecurr();

        tvTaskName.setText(title);

        Date endDateValue = null;
        String strEndDate = strDuedate;
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy");
        try {
            endDateValue = df.parse(strEndDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Date startDateValue = Calendar.getInstance().getTime();
        /*
        long diff = endDateValue.getTime()
                - startDateValue.getTime();
        int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        tvDaysLeft.setText(days);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        String dayOfTheWeek = sdf.format(endDateValue);

        tvDay.setText(dayOfTheWeek);
        */
        tvDuedate.setText(strDuedate);

        tvDescription.setText(description);

        if (!isRecurr) {
            ivRecurr.setImageResource(android.R.color.transparent);
        }
        else if (isRecurr) {
            ivRecurr.setImageResource(R.drawable.recurring);
        }
        //}

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
                            //TODO how to notify/update adapter
                        }
                        else {
                            db.deleteTask(id);
                        }

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
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteTask(id);
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        return alertDialog;
    }
}

