package edu.dlsu.mobapde.icandov3;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class SearchActivity extends AppCompatActivity {

    ProgressBar pgLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_gradient));

        pgLevel = findViewById(R.id.pg_bar);
        pgLevel.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#11ffb6")));
    }
}
