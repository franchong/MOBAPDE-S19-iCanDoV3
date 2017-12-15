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

    ImageView ivCurr;
    TextView tvTaskName, tvDaysLeft, tvDuedate, tvDay, tvDescription;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_view_task_menu, null);

        tvTaskName = (TextView) v.findViewById(R.id.tv_taskname);
        tvDaysLeft = (TextView) v.findViewById(R.id.tv_daysleft);
        tvDay = (TextView) v.findViewById(R.id.tv_day);
        tvDuedate = (TextView) v.findViewById(R.id.tv_duedate);
        tvDescription = (TextView) v.findViewById(R.id.tv_description);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            long id = bundle.getLong(Task.COLUMN_ID);
            String title = bundle.getString(Task.COLUMN_TITLE);
            String description = bundle.getString(Task.COLUMN_DESC);
            String strDuedate = bundle.getString(Task.COLUMN_DUEDATE);
            String strCreatedate = bundle.getString(Task.COLUMN_CREATIONDATE);
            long categoryID = bundle.getLong(Task.COLUMN_CAT);
            boolean isRecurr = bundle.getBoolean(Task.COLUMN_RECURR);

            tvTaskName.setText(title);

            Date endDateValue = null;
            String strEndDate = strDuedate;
            SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy");
            try {
                endDateValue = df.parse(strEndDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Date startDateValue = Calendar.getInstance().getTime();
            long diff = endDateValue.getTime() - startDateValue.getTime();
            int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            tvDaysLeft.setText(days);

            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            String dayOfTheWeek = sdf.format(endDateValue);
            tvDay.setText(dayOfTheWeek);

            tvDuedate.setText(strDuedate);

            tvDescription.setText(description);
        }
        
        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("Complete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dismiss();
                    }
                })
                .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setClass(getActivity(), AddEditTaskMenu.class);
                        startActivityForResult(intent, 1);
                        dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        return alertDialog;
    }
}

