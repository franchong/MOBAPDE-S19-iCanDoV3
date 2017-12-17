package edu.dlsu.mobapde.icandov3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AddEditRewardMenu extends AppCompatActivity {

    ProgressBar pgLevel;
    ImageView ivRepeatable;
    EditText etName, etDescription, etPoints;
    Button buttonCancel, buttonSave, buttonDelete;
    AlertDialog alertDialog;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_reward_menu);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_gradient));

        pgLevel = findViewById(R.id.pg_bar);
        pgLevel.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#11ffb6")));

        etName         = findViewById(R.id.et_name);
        etDescription  = findViewById(R.id.et_description);
        etPoints       = findViewById(R.id.et_points);
        buttonCancel   = findViewById(R.id.button_cancel);
        buttonSave     = findViewById(R.id.button_save);
        buttonDelete   = findViewById(R.id.button_delete);
        ivRepeatable   = findViewById(R.id.iv_repeatable);

        dbHelper = new DatabaseHelper(getBaseContext());
        final boolean isEdit = getIntent().getBooleanExtra("isEdit", false);

        if (isEdit) {

            buttonDelete.setVisibility(View.VISIBLE);
            etName.setText(getIntent().getStringExtra(Reward.COLUMN_TITLE));
            etDescription.setText(getIntent().getStringExtra(Reward.COLUMN_DESC));
            int p = getIntent().getIntExtra(Reward.COLUMN_POINTS, 0);
            etPoints.setText(String.valueOf(p));

            boolean isRepeatable = getIntent().getBooleanExtra(Reward.COLUMN_REPEATABLE, true);

            if (!isRepeatable) {
                ivRepeatable.setImageResource(R.drawable.nonrecurring);
                ivRepeatable.setTag("false");
            } else {
                ivRepeatable.setImageResource(R.drawable.recurring);
                ivRepeatable.setTag("true");
            }

        } else {

            buttonDelete.setVisibility(View.GONE);
            ivRepeatable.setTag("false");

        }

        ivRepeatable.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(ivRepeatable.getTag() != null && ivRepeatable.getTag().toString().equals("false")){
                    ivRepeatable.setImageResource(R.drawable.recurring);
                    ivRepeatable.setTag("true");
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Reward is set to repeatable.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                } else {
                    ivRepeatable.setImageResource(R.drawable.nonrecurring);
                    ivRepeatable.setTag("false");
                    Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Reward is set to nonrepeatable.", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }

            }

        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Reward reward;


                if (isEdit) {
                    long id = getIntent().getLongExtra("id_", 1);

                    reward = dbHelper.getReward(id);
                } else {
                    reward = new Reward();
                }


                if (etName.getText().toString() != "" && etPoints.getText().toString() != "") {

                    reward.setTitle(etName.getText().toString());
                    reward.setDescription(etDescription.getText().toString());
                    reward.setPoints(Integer.parseInt(String.valueOf(etPoints.getText())));
                    if (ivRepeatable.getTag().toString().equals("false"))
                        reward.setRepeatable(false);
                    else
                        reward.setRepeatable(true);
                    reward.setPoints(Integer.parseInt(etPoints.getText().toString()));

                    if (!isEdit) {
                        dbHelper.addReward(reward);
                    } else {
                        boolean edited = dbHelper.editReward(reward, reward.getId());
                        Log.i("edited", String.valueOf(edited));
                    }

                    Intent i = new Intent();
                    i.setAction(Intent.ACTION_CALL);
                    i.setClass(getBaseContext(), RewardListActivity.class);
                    startActivityForResult(i, 11);

                } else {

                    alertDialog = new AlertDialog.Builder(getBaseContext()).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("No reward name found!");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long id = getIntent().getLongExtra("id_", 1);
                dbHelper.deleteReward(id);
                finish();
            }
        });


    }
}

