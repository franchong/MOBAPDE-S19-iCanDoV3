package edu.dlsu.mobapde.icandov3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
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

public class RewardListActivity extends AppCompatActivity {

    ProgressBar pgLevel;
    RecyclerView rvRewards;
    LinearLayout llSort, llSearch, llCategories;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fabCategory, fabTask, fabReward;
    DatabaseHelper dbHelper;
    RewardAdapter rewardAdapter;
    TextView tvPoints, tvLevel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_gradient));

        pgLevel = findViewById(R.id.pg_bar);
        pgLevel.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#11ffb6")));

        llSort = findViewById(R.id.ll_sort);
        llSearch = findViewById(R.id.ll_search);
        llCategories = findViewById(R.id.ll_categories);

        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), SearchActivity.class);
                startActivityForResult(i, 0);
            }
        });

        llSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortRewardsDialog sd = new SortRewardsDialog();
                sd.show(getFragmentManager(), "");
            }
        });

        llCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), CategoryListActivity.class);
                startActivityForResult(i, 0);

            }
        });

        rvRewards = (RecyclerView) findViewById(R.id.rv_rewards);

        dbHelper = new DatabaseHelper(getBaseContext());


        rewardAdapter = new RewardAdapter(getBaseContext(), dbHelper.getAllRewardCursor());

        rewardAdapter.setOnItemClickListener(new RewardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Long r) {

                Bundle bundle = new Bundle();
                bundle.putLong("id_", r);

                ViewRewardMenu sd = new ViewRewardMenu();
                sd.setArguments(bundle);

                sd.show(getFragmentManager(), "");

            }
        });

        rvRewards.setAdapter(rewardAdapter);

        rvRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        rvRewards.setLayoutManager(new LinearLayoutManager(
                getBaseContext(), LinearLayoutManager.VERTICAL, false
        ));

        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.fab_menu);
        fabCategory = (FloatingActionButton) findViewById(R.id.fab_category);
        fabTask = (FloatingActionButton) findViewById(R.id.fab_task);
        fabReward = (FloatingActionButton) findViewById(R.id.fab_reward);

        fabCategory.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CategoryDialog sd = new CategoryDialog();
                sd.show(getFragmentManager(), "");
            }
        });

        fabTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), AddEditTaskMenu.class);
                startActivityForResult(i, 10);
            }
        });

        fabReward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), AddEditRewardMenu.class);
                startActivityForResult(i, 11);
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
        rewardAdapter.changeCursor(dbHelper.getAllRewardCursor());
    }

    public void update(boolean purchased) {
        rewardAdapter.changeCursor(dbHelper.getAllRewardCursor());
        if (purchased) {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Congratulations, You have purchased a reward", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
        else {
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "You need more points for this reward", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }

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

        if (priority.equalsIgnoreCase("Name"))
            prio = Reward.COLUMN_TITLE;
        if (priority.equalsIgnoreCase("Points"))
            prio = Reward.COLUMN_POINTS;

        if (order.equalsIgnoreCase("Ascending"))
            ord = "ASC";
        if (order.equalsIgnoreCase("Descending"))
            ord = "DESC";

        Log.d("TAG", prio + " " + ord);
        rewardAdapter.changeCursor(dbHelper.getAllRewardCursor(prio, ord));
    }
}