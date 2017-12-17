package edu.dlsu.mobapde.icandov3;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    ProgressBar pgLevel;
    LinearLayout llSearch, llSort, llRewards;
    RecyclerView rvTasks;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fabCategory, fabTask, fabReward;
    DatabaseHelper db;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    TextView tvCategory;
    TaskAdapter ta;
    TextView tvPoints, tvLevel;

    long categoryid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        db = new DatabaseHelper(getBaseContext());
        tvCategory = findViewById(R.id.tv_category);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_gradient));

        categoryid = getIntent().getExtras().getLong("CategoryID");
        Category category = db.getCategory(categoryid);
        tvCategory.setText(category.getName());

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

        llSort = findViewById(R.id.ll_sort);

        llSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortDialog sd = new SortDialog();
                sd.show(getFragmentManager(), "");
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

        rvTasks = findViewById(R.id.rv_tasks);

        final ArrayList<Task> tasks = new ArrayList<>();

        ta = new TaskAdapter(getBaseContext(), db.getAllTasksCursor(categoryid));
        ta.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(long r) {

                Bundle bundle = new Bundle();
                bundle.putLong("id_", r);

                ViewTaskMenu sd = new ViewTaskMenu();
                sd.setArguments(bundle);

                sd.show(getFragmentManager(), "");
            }
        });
        rvTasks.setAdapter(ta);

        rvTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        rvTasks.setLayoutManager(new LinearLayoutManager(
                getBaseContext(), LinearLayoutManager.VERTICAL, false
        ));

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                AlertDialog.Builder alert = new AlertDialog.Builder(TaskListActivity.this);
                alert.setTitle("Complete");
                alert.setMessage("Done with the task?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = viewHolder.getAdapterPosition();
                        tasks.remove(position);
                        ta.notifyDataSetChanged();
                        dialog.dismiss();

                        AlertDialog alertDialog = new AlertDialog.Builder(TaskListActivity.this).create();
                        alertDialog.setTitle("Rewards");
                        alertDialog.setMessage("You earned 10 points!");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();
                    }
                });

                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int position = viewHolder.getAdapterPosition();
                        ta.notifyItemChanged(position);
                        dialog.dismiss();
                    }
                });


                Dialog dialog = alert.create();

                alert.show();
                dialog.getWindow().setBackgroundDrawableResource(android.R.color.background_dark);

            }

        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvTasks);

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
                i.putExtra("CategoryID", categoryid);
                startActivityForResult(i, 8);
            }
        });

        fabReward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), AddEditRewardMenu.class);
                startActivityForResult(i, 9);
            }
        });

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

    @Override
    protected void onResume() {
        super.onResume();
        ta.changeCursor(db.getAllTasksCursor(categoryid));

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

    protected void refresh(String priority, String order) {
        super.onResume();

        String prio = null;
        String ord = null;

        Log.d("TAG", priority);
        Log.d("TAG", order);

        if( priority.equalsIgnoreCase("Name"))
            prio = Task.COLUMN_TITLE;
        if( priority.equalsIgnoreCase("Due Date"))
            prio = Task.COLUMN_DUEDATE;
        if( priority.equalsIgnoreCase("Creation Date"))
            prio = Task.COLUMN_CREATIONDATE;

        if( order.equalsIgnoreCase("Ascending"))
            ord = "ASC";
        if( order.equalsIgnoreCase("Descending"))
            ord = "DESC";

        Log.d("TAG", prio + " " + ord);
        ta.changeCursor(db.getAllTasksCursor(prio, ord, categoryid));
    }
}
