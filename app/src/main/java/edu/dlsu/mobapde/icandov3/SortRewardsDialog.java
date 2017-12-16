package edu.dlsu.mobapde.icandov3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by Dell on 12/09/2017.
 */

public class SortRewardsDialog extends DialogFragment {

    RadioGroup radioGroupPriority;
    RadioGroup radioGroupOrder;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        final View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_sort_rewards, null);

        radioGroupPriority = v.findViewById(R.id.rg_priority);
        radioGroupOrder = v.findViewById(R.id.rg_order);


        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int indexPriority = radioGroupPriority.getCheckedRadioButtonId();
                        int indexOrder = radioGroupOrder.getCheckedRadioButtonId();

                        RadioButton priority = v.findViewById(indexPriority);
                        RadioButton order = v.findViewById(indexOrder);

                        String p = priority.getText().toString();
                        String o = order.getText().toString();

                        Log.d("TAG", priority.getText().toString());
                        Log.d("TAG", order.getText().toString());

                        ((RewardListActivity)getActivity()).refresh(p,o);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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