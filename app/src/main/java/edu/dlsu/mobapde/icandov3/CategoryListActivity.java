package edu.dlsu.mobapde.icandov3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Locale;

public class CategoryListActivity extends AppCompatActivity {

    ProgressBar pgLevel;
    LinearLayout llSearch, llRewards;
    RecyclerView rvCategories;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fabCategory, fabTask, fabReward;
    CategoryAdapter ca;
    DatabaseHelper dbHelper;


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

    }

    //TODO update database and add snackbar notification
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 3 && resultCode == RESULT_OK) {

        }
        if (requestCode == 3 && resultCode == RESULT_CANCELED) {

        }

        if (requestCode == 4 && resultCode == RESULT_OK) {

        }
        if (requestCode == 4 && resultCode == RESULT_CANCELED) {

        }
    }

    public void refresh() {
        Log.d("TAG", "IM BACK");
        super.onResume();
        ca.changeCursor(dbHelper.getAllCategoriesCursor());
        ca.notifyDataSetChanged();
    }
}
