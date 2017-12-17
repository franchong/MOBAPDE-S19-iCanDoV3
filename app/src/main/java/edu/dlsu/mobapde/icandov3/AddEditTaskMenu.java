package edu.dlsu.mobapde.icandov3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;

public class AddEditTaskMenu extends AppCompatActivity {

    ProgressBar pgLevel;
    TextView tvDate;
    ImageView ivRecurring, ivDate;
    Button btnCancel, btnSave, btnDelete;
    EditText etName, etDescription;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_gradient));

        dbHelper = new DatabaseHelper(getBaseContext());

        pgLevel = findViewById(R.id.pg_bar);
        pgLevel.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#11ffb6")));

        ivDate = (ImageView) findViewById(R.id.iv_date);
        btnCancel = (Button) findViewById(R.id.button_cancel);
        btnSave = (Button) findViewById(R.id.button_save);
        btnDelete = (Button) findViewById(R.id.button_delete);

        ivRecurring = (ImageView) findViewById(R.id.iv_recurring);
        tvDate = (TextView) findViewById(R.id.tv_date);
        etName = (EditText) findViewById(R.id.et_name);
        etDescription = (EditText) findViewById(R.id.et_description);

        boolean isEdit = getIntent().getBooleanExtra("isEdit", false);

        if (isEdit) {
            btnDelete.setVisibility(View.VISIBLE);
            boolean isRecurr = getIntent().getBooleanExtra(Task.COLUMN_RECURR, false);
            tvDate.setText(getIntent().getStringExtra(Task.COLUMN_DUEDATE));
            etName.setText(getIntent().getStringExtra(Task.COLUMN_TITLE));
            etDescription.setText(getIntent().getStringExtra(Task.COLUMN_DESC));

            if (isRecurr) {
                ivRecurring.setImageResource(R.drawable.recurring);
            }
            else {
                ivRecurring.setImageResource(R.drawable.nonrecurring);
            }
        } else {
            btnDelete.setVisibility(View.GONE);
            ivRecurring.setTag("nonrecurring");
        }


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

        ivRecurring.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(ivRecurring.getTag() != null && ivRecurring.getTag().toString().equals("nonrecurring")){
                    ivRecurring.setImageResource(R.drawable.recurring);
                    ivRecurring.setTag("recurring");
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Task is set to daily.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    ivRecurring.setImageResource(R.drawable.nonrecurring);
                    ivRecurring.setTag("nonrecurring");
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Task is set to once.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();

                boolean recurring = false;
                if (ivRecurring.getTag().toString().equals("nonrecurring")) {
                    recurring = false;
                }
                else if (ivRecurring.getTag().toString().equals("recurring")) {
                    recurring = true;
                }

                long catergoryID = getIntent().getExtras().getLong("CategoryID");

                Task task = new Task();
                task.setTitle(etName.getText().toString());
                task.setDescription(etDescription.getText().toString());
                task.setCreatedate(Calendar.getInstance().getTime().toString());
                task.setDuedate(tvDate.getText().toString());
                task.setCategoryID(catergoryID);
                task.setRecurr(recurring);

                dbHelper.addTask(task);

                setResult(RESULT_OK, data);
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long id = getIntent().getLongExtra(Task.COLUMN_ID, 1);
                dbHelper.deleteTask(id);
                finish();
            }
        });

    }


}
