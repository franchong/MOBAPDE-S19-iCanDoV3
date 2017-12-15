package edu.dlsu.mobapde.icandov3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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

public class TaskListActivity extends AppCompatActivity {

    ProgressBar pgLevel;
    LinearLayout llSearch, llSort, llRewards;
    RecyclerView rvTasks;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fabCategory, fabTask, fabReward;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        db = new DatabaseHelper(getBaseContext());

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



        final TaskAdapter ta = new TaskAdapter(getBaseContext(), db.getAllTasksCursor());
        ta.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Task t) {

                //TODO parsing date might cause error
                Bundle bundle = new Bundle();
                bundle.putString(Task.COLUMN_TITLE, t.getTitle());
                bundle.putString(Task.COLUMN_DESC, t.getDescription());
                SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy");
                String strDueDate = df.format(t.getDuedate());
                String strCreateDate = df.format(t.getCreatedate());

                bundle.putString(Task.COLUMN_CREATIONDATE, strCreateDate);
                bundle.putString(Task.COLUMN_DUEDATE, strDueDate);
                bundle.putLong(Task.COLUMN_CAT, t.getCategoryID());
                bundle.putLong(Task.COLUMN_ID, t.getId());
                bundle.putBoolean(Task.COLUMN_RECURR, t.isRecurr());

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
    }

    //TODO update database and add snackbar notification
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        RecyclerView.Adapter adapter = rvTasks.getAdapter();

        if(requestCode == 3 && resultCode == RESULT_OK) {

            String title = data.getExtras().getString(Task.COLUMN_TITLE);
            String desc = data.getExtras().getString(Task.COLUMN_DESC);
            Date creatDate = Calendar.getInstance().getTime();

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
            Task task = new Task(0, title, desc, dueDate, creatDate, 0, recurr);
            db.addTask(task);

            //TODO does this notify/update the recycler view?
            adapter.notifyItemInserted(adapter.getItemCount()-1);

            //TODO snackbar https://developer.android.com/training/snackbar/action.html
        }

        if(requestCode == 4 && resultCode == RESULT_OK) {

        }

    }
}
