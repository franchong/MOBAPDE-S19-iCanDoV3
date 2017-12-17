package edu.dlsu.mobapde.icandov3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.Calendar;

public class CategoryListActivity extends AppCompatActivity {

    ProgressBar pgLevel;
    LinearLayout llSearch, llRewards;
    RecyclerView rvCategories;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fabCategory, fabTask, fabReward;
    CategoryAdapter ca;
    DatabaseHelper dbHelper;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    TextView tvPoints, tvLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        dbHelper = new DatabaseHelper(getBaseContext());

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
                startActivityForResult(i, 0);
            }
        });

        llRewards = findViewById(R.id.ll_rewards);

        llRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), RewardListActivity.class);
                startActivityForResult(i, 0);
            }
        });

        rvCategories = (RecyclerView) findViewById(R.id.rv_categories);

        ca = new CategoryAdapter(getBaseContext(),
                dbHelper.getAllCategoriesCursor());
        ca.setOnItemClickListener(new CategoryAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Long c) {
                //TODO lead the user to the taskListActivity with the correct category
                Log.d("TAG", "Category is clicked");
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), TaskListActivity.class);
                intent.putExtra("CategoryID", c);
                Log.d("TAG", "ID IS :" +c+"");

                startActivity(intent);

            }
        });
        rvCategories.setAdapter(ca);

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

        //TODO Notification Alarm Integrated
        alarmMgr = (AlarmManager)getBaseContext().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(getBaseContext(), 1, i, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 1); //1AM //TODO test

        alarmMgr.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + 1*1000, alarmIntent);
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);

        //TODO User Points
        int currentPoints, currentLevel;

        tvPoints = findViewById(R.id.tv_points);
        tvLevel = findViewById(R.id.tv_level);

        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor dspEditor = dsp.edit();

        currentPoints = dsp.getInt(User.COLUMN_POINTS, -1);
        if (currentPoints == -1) {
            dspEditor.putInt(User.COLUMN_POINTS, 0);
            currentPoints = 0;
        }
        currentLevel = dsp.getInt(User.COLUMN_LEVEL, -1);
        if (currentLevel == -1) {
            dspEditor.putInt(User.COLUMN_LEVEL, 1);
            currentLevel = 1;
        }
        dspEditor.apply();

        tvPoints.setText(Integer.toString(currentPoints));
        tvLevel.setText("Level " + Integer.toString(currentLevel));
        pgLevel.setProgress(currentPoints);

//        Log.i("LOG currentpoints", Integer.toString(currentPoints));
//        Log.i("LOG currentlevel", Integer.toString(currentLevel));
//        Log.i("LOG actual points", String.valueOf(dsp.getInt(User.COLUMN_POINTS, -1)));
//        Log.i("LOG actual level", String.valueOf(dsp.getInt(User.COLUMN_LEVEL, -1)));

    }

    public void refresh() {
        Log.d("TAG", "IM BACK");
        super.onResume();
        ca.changeCursor(dbHelper.getAllCategoriesCursor());
        ca.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        //TODO User Points
        int currentPoints, currentLevel;

        tvPoints = findViewById(R.id.tv_points);
        tvLevel = findViewById(R.id.tv_level);

        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences.Editor dspEditor = dsp.edit();

        currentPoints = dsp.getInt(User.COLUMN_POINTS, -1);
        currentLevel = dsp.getInt(User.COLUMN_LEVEL, -1);

        tvPoints.setText(Integer.toString(currentPoints));
        tvLevel.setText("Level " + Integer.toString(currentLevel));
        pgLevel.setProgress(currentPoints);
    }
}
