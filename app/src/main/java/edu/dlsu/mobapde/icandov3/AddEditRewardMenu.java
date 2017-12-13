package edu.dlsu.mobapde.icandov3;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AddEditRewardMenu extends AppCompatActivity {

    ProgressBar pgLevel;
    ImageView ivRecurring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_reward_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_gradient));

        pgLevel = findViewById(R.id.pg_bar);
        pgLevel.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#11ffb6")));

        ivRecurring = (ImageView) findViewById(R.id.iv_recurring);

        ivRecurring.setTag("nonrecurring");

        ivRecurring.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(ivRecurring.getTag() != null && ivRecurring.getTag().toString().equals("nonrecurring")){
                    ivRecurring.setImageResource(R.drawable.recurring);
                    ivRecurring.setTag("recurring");
                    // add snackbar
                } else {
                    ivRecurring.setImageResource(R.drawable.nonrecurring);
                    ivRecurring.setTag("nonrecurring");
                    // add snackbar
                }

            }

        });
    }
}
