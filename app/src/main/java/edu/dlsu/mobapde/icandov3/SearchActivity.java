package edu.dlsu.mobapde.icandov3;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class SearchActivity extends AppCompatActivity {

    ProgressBar pgLevel;
    LinearLayout llSort, llSearch;
    FloatingActionMenu floatingActionMenu;
    FloatingActionButton fabCategory, fabTask, fabReward;
    DatabaseHelper dbHelper;
    AutoCompleteTextView etSearch;
    SearchAdapter sa;
    RecyclerView rvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        dbHelper = new DatabaseHelper(getBaseContext());

        etSearch = findViewById(R.id.tv_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_gradient));

        pgLevel = findViewById(R.id.pg_bar);
        pgLevel.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#11ffb6")));

        llSort = findViewById(R.id.ll_sort);

        llSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SortSearchDialog sd = new SortSearchDialog();
                sd.show(getFragmentManager(), "");
            }
        });

        llSearch = findViewById(R.id.ll_search);

        llSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String search = etSearch.getText().toString();
                refresh(search);
            }
        });

        rvSearch = findViewById(R.id.rv_search);

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

    public void refresh( String search) {

        Log.d("TAG", "Searching for " + search);
        super.onResume();
        sa = new SearchAdapter(getBaseContext(), dbHelper.getSearchCategory(search));
        sa.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(Long c) {
                //TODO lead the user to the taskListActivity with the correct category
                Log.d("TAG", "Here in Search Activity");
                Log.d("TAG", "Item is clicked");
                Intent intent = new Intent();
                intent.setClass(getBaseContext(), CategoryListActivity.class);

                intent.putExtra("itemID", c);
                Log.d("TAG", "ID IS :" + c + "");

                etSearch.setText("");
                startActivity(intent);

            }
        });
        rvSearch.setAdapter(sa);

        rvSearch.setLayoutManager(new LinearLayoutManager(
                getBaseContext(), LinearLayoutManager.VERTICAL, false
        ));
    }

}


