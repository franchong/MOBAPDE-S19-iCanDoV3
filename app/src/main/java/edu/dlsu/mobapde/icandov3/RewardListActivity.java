package edu.dlsu.mobapde.icandov3;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

public class RewardListActivity extends AppCompatActivity {

    ProgressBar pgLevel;
    LinearLayout llSearch, llSort, llCategories;
    RecyclerView rvRewards;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fabCategory, fabTask, fabReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_list);

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

        llCategories = findViewById(R.id.ll_categories);
        llCategories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(getBaseContext(), CategoryListActivity.class);
                startActivityForResult(i, 2);
            }
        });

        rvRewards = (RecyclerView) findViewById(R.id.rv_rewards);

        final ArrayList<Reward> rewards = new ArrayList<>();

        rewards.add(new Reward(R.drawable.menu, "Relax", "Lorem ipsum dolor sit amet, consectur adipsicing elit", 25));

        final RewardAdapter ra = new RewardAdapter(rewards);
        ra.setOnItemClickListener(new RewardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reward r) {
                ViewRewardMenu sd = new ViewRewardMenu();
                sd.show(getFragmentManager(), "");
            }
        });
        rvRewards.setAdapter(ra);

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
                //TODO something when floating action menu first item clicked

            }
        });

        fabTask.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu second item clicked

            }
        });

        fabReward.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //TODO something when floating action menu third item clicked

            }
        });
    }
}
