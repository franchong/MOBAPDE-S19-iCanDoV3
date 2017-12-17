package edu.dlsu.mobapde.icandov3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * Created by Dell on 12/09/2017.
 */

public class SortDialog extends DialogFragment {

    RadioGroup radioGroupPriority;
    RadioGroup radioGroupOrder;

    public Dialog onCreateDialog(Bundle saveInstanceState) {
        final View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_sort, null);

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
                            if(p != null && o != null) {
                                Log.d("TAG", priority.getText().toString());
                                Log.d("TAG", order.getText().toString());

                                ((TaskListActivity) getActivity()).refresh(p, o);
                            }
                            dismiss();
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dismiss();
                        }
                    });

        return builder.create();
    }

}
