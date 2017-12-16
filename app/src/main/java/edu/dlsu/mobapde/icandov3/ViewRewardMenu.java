package edu.dlsu.mobapde.icandov3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

public class ViewRewardMenu extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_view_reward_menu, null);

        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("Purchase", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SharedPreferences dsp = PreferenceManager.getDefaultSharedPreferences(getContext());
                        SharedPreferences.Editor dspEditor = dsp.edit(); // has write access
                        int points = dsp.getInt(User.COLUMN_POINTS, -1);
                        points --;
                        dspEditor.putInt(User.COLUMN_POINTS, points);
                        dspEditor.apply();
                        dismiss();
                    }
                })
                .setNeutralButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dismiss();
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        return alertDialog;
    }
}
