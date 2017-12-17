package edu.dlsu.mobapde.icandov3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewRewardMenu extends DialogFragment {

    ImageView ivRepeatable;
    TextView tvRewardName, tvDescription, tvPoints;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        final View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_view_reward_menu, null);

        final DatabaseHelper db = new DatabaseHelper(v.getContext());

        tvRewardName  = v.findViewById(R.id.tv_rewardname);
        tvDescription = v.findViewById(R.id.tv_description);
        tvPoints      = v.findViewById(R.id.tv_points);
        ivRepeatable  = v.findViewById(R.id.iv_repeatable) ;

        Bundle bundle = this.getArguments();
        final long id = bundle.getLong("id_");

        Reward reward = db.getReward(id);

        final String title         = reward.getTitle();
        final String description   = reward.getDescription();
        final int points           = reward.getPoints();
        final boolean isRepeatable = reward.isRepeatable();

        tvRewardName.setText(title);
        tvDescription.setText(description);
        tvPoints.setText(points + " Points");
        if (!isRepeatable) {
            ivRepeatable.setImageResource(R.drawable.nonrecurring);
        }
        else if (isRepeatable) {
            ivRepeatable.setImageResource(R.drawable.recurring);
        }

        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("Purchase", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //TODO User Points
                        int currentPoints;
                        //TODO v made final type
                        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(v.getContext());
                        SharedPreferences.Editor dspEditor = dsp.edit();

                        currentPoints = dsp.getInt(User.COLUMN_POINTS, -1);

                        if (currentPoints >= points) {
                            // if user has enough points
                            if (!isRepeatable) {
                                db.deleteReward(id);
                                ((RewardListActivity)getActivity()).update(true);
                            } else {
                                ((RewardListActivity)getActivity()).update(true);
                            }

                            currentPoints  = currentPoints - points;
                            dspEditor.putInt(User.COLUMN_POINTS, currentPoints);
                            dspEditor.apply();
                        }
                        else {
                            ((RewardListActivity)getActivity()).update(false);
                        }



                        dismiss();
                    }
                })
                .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setClass(getActivity(), AddEditRewardMenu.class);
                        intent.putExtra("isEdit", true);
                        intent.putExtra("id_", id);
                        intent.putExtra(Reward.COLUMN_TITLE, title);
                        intent.putExtra(Reward.COLUMN_DESC, description);
                        intent.putExtra(Reward.COLUMN_POINTS, points);
                        intent.putExtra(Reward.COLUMN_REPEATABLE, isRepeatable);
                        startActivityForResult(intent, 15);
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteReward(id);
                        ((RewardListActivity)getActivity()).update(false);
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        return alertDialog;
    }
}

