package edu.dlsu.mobapde.icandov3;

import android.app.DatePickerDialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddEditTaskMenu extends AppCompatActivity {

    ProgressBar pgLevel;
    TextView tvDate;
    ImageView ivRecurring, ivDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_gradient));

        pgLevel = findViewById(R.id.pg_bar);
        pgLevel.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#11ffb6")));

        tvDate = (TextView) findViewById(R.id.tv_date);
        ivRecurring = (ImageView) findViewById(R.id.iv_recurring);
        ivDate = (ImageView) findViewById(R.id.iv_date);

        Calendar c = Calendar.getInstance();
        final int y = c.get(Calendar.YEAR);
        final int m = c.get(Calendar.MONTH);
        final int d = c.get(Calendar.DAY_OF_MONTH);
        int yr;
        String mm;
        String dd;
        String yy;

        if (m+1 < 10)
            mm = 0 + Integer.toString(m+1);
        else
            mm = Integer.toString(m+1);

        if (d < 10)
            dd = 0 + Integer.toString(d);
        else
            dd = Integer.toString(d);

        yr = y % 100;
        yy = Integer.toString(yr);

        ivDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(AddEditTaskMenu.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String mm_;
                        String dd_;
                        String yy_;

                        if (month+1 < 10)
                            mm_ = 0 + Integer.toString(month+1);
                        else
                            mm_ = Integer.toString(month+1);

                        if (day < 10)
                            dd_ = 0 + Integer.toString(day);
                        else
                            dd_ = Integer.toString(day);

                        year = year % 100;
                        yy_ = Integer.toString(year);

                        tvDate.setText(mm_ + "/" + dd_ + "/" + yy_);
                    }
                }, y, m, d);
                datePickerDialog.show();

            }

        });

        ivRecurring.setTag("nonrecurring");

        ivRecurring.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(ivRecurring.getTag() != null && ivRecurring.getTag().toString().equals("nonrecurring")){
                    ivRecurring.setImageResource(R.drawable.recurring);
                    ivRecurring.setTag("recurring");
                } else {
                    ivRecurring.setImageResource(R.drawable.nonrecurring);
                    ivRecurring.setTag("nonrecurring");
                }

            }

        });

    }
}
