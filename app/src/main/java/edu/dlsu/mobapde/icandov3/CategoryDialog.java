package edu.dlsu.mobapde.icandov3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Dell on 12/08/2017.
 */

public class CategoryDialog extends DialogFragment {
    TextView tvCategoryName;
    EditText etTitle;
    DatabaseHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_category, null);
        tvCategoryName = (TextView) v.findViewById(R.id.tv_categoryname);
        etTitle = (EditText) v.findViewById(R.id.et_title);
        dbHelper = new DatabaseHelper(getContext());

        AlertDialog.Builder builder
                = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Log.d("TAG","HERE IN CATEGORY DIALOG");

                        //TODO get category name and update rv and db
                        Category category = new Category();
                        category.setName(etTitle.getText().toString());
                        dbHelper.addCategory(category);
                        ((CategoryListActivity)getActivity()).refresh();
                        dismiss();
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dismiss();
                        ((CategoryListActivity)getActivity()).refresh();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);

        return alertDialog;
    }
}
