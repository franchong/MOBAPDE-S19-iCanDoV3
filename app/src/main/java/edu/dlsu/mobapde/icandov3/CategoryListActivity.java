package edu.dlsu.mobapde.icandov3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CategoryListActivity extends AppCompatActivity {

    ProgressBar pgLevel;
    LinearLayout llSearch, llRewards;
    RecyclerView rvCategories;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fabCategory, fabTask, fabReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_gradient));

        pgLevel = findViewById(R.id.pg_bar);
        pgLevel.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#11ffb6")));

        llSearch = findViewById(R.id.ll_search);

        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), SearchActivity.class);
                startActivityForResult(i, 1);
                /*ViewTaskMenu cd = new ViewTaskMenu();
                cd.show(getFragmentManager(), "");*/
            }
        });

        llRewards = findViewById(R.id.ll_rewards);
        llRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), RewardListActivity.class);
                startActivityForResult(i, 2);
            }
        });

        rvCategories = (RecyclerView) findViewById(R.id.rv_categories);

        final ArrayList<Category> categories = new ArrayList<>();

        /*categories.add(new Category(R.drawable.menu, "All Tasks", 20));
        categories.add(new Category(R.drawable.menu, "Due Today", 5));
        categories.add(new Category(R.drawable.menu, "Chores", 3));
        categories.add(new Category(R.drawable.menu, "Academics", 20));
        categories.add(new Category(R.drawable.menu, "Important", 5));
        categories.add(new Category(R.drawable.menu, "Habits", 3));
        categories.add(new Category(R.drawable.menu, "Outing", 20));*/

        final CategoryAdapter ca = new CategoryAdapter(categories);
        ca.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Category c) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), TaskListActivity.class);
                startActivityForResult(i, 0);
            }
        });
        rvCategories.setAdapter(ca);

        rvCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        rvCategories.setLayoutManager(new LinearLayoutManager(
                getBaseContext(), LinearLayoutManager.VERTICAL, false
        ));

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fabCategory = (FloatingActionButton) findViewById(R.id.fab_category);
        fabTask = (FloatingActionButton) findViewById(R.id.fab_task);
        fabReward = (FloatingActionButton) findViewById(R.id.fab_reward);

        fabCategory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu first item clicked
                CategoryDialog sd = new CategoryDialog();
                sd.show(getFragmentManager(), "");
            }
        });

        fabTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), AddEditTaskMenu.class);
                i.putExtra("isEdit", false);
                startActivityForResult(i, 3);
            }
        });

        fabReward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), AddEditRewardMenu.class);
                startActivityForResult(i, 4);
            }
        });

        //TODO Test Complete Notifications Functional
        AlarmManager alarmMgr;
        PendingIntent alarmIntent;

        alarmMgr = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getBaseContext(), 1, i, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1*1000, alarmIntent);

    }

    //TODO update database and add snackbar notification
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        DatabaseHelper db = new DatabaseHelper(getBaseContext());

        if(requestCode == 3 && resultCode == RESULT_OK) {

            boolean isEdit = getIntent().getBooleanExtra("isEdit", false);

            String title = data.getExtras().getString(Task.COLUMN_TITLE);
            String desc = data.getExtras().getString(Task.COLUMN_DESC);
            Date createDate = Calendar.getInstance().getTime();

            String strDueDate = data.getExtras().getString(Task.COLUMN_DUEDATE);
            Date dueDate = null;
            SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy");
            try {
                dueDate = df.parse(strDueDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            boolean recurr = data.getExtras().getBoolean(Task.COLUMN_RECURR);

            //TODO interface: add a category selection option, then add category to Task
            //TODO long ID and Category ID
            Task task = new Task(0, title, desc, dueDate, createDate, 0, recurr);

            if (isEdit) {
                db.editTask(task, getIntent().getLongExtra(Task.COLUMN_ID, 0));
                //TODO does this notify/update the recycler view?
            }
            else {
                db.addTask(task);
                //TODO does this notify/update the recycler view?
            }
            //TODO snackbar to show user it has been added: https://developer.android.com/training/snackbar/action.html

        }

        if(requestCode == 4 && resultCode == RESULT_OK) {

        }

    }
}
