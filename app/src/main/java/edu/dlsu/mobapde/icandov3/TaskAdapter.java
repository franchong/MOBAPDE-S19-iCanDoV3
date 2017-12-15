package edu.dlsu.mobapde.icandov3;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dell on 12/08/2017.
 */

public class TaskAdapter extends CursorRecyclerViewAdapter<TaskAdapter.TaskViewHolder> {

    //TODO context1 unsure of the way its used: for starting an intent
    public Context context1;

    public TaskAdapter(Context context, Cursor cursor) {
        super(context, cursor);
        context1 = context;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new TaskViewHolder(v);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMenu;
        TextView tvLeft, tvRight;

        public TaskViewHolder(View itemView) {
            super(itemView);
            ivMenu = itemView.findViewById(R.id.iv_menu);
            tvLeft = itemView.findViewById(R.id.tv_left);
            tvRight = itemView.findViewById(R.id.tv_right);
        }
    }

    @Override
    public void onBindViewHolder(TaskViewHolder viewHolder, final Cursor cursor) {
        //TODO what comes out of here?
        //TODO retrieve Date value, calculate Days left, then setText
        String endDate = cursor.getString(cursor.getColumnIndex(Task.COLUMN_DUEDATE));
        Date endDateValue = new Date();
        Date startDateValue = Calendar.getInstance().getTime();
        long diff = endDateValue.getTime() - startDateValue.getTime();
        int days = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        viewHolder.tvRight.setText(days);

        String left = cursor.getColumnName(cursor.getColumnIndex(Task.COLUMN_TITLE));
        viewHolder.tvLeft.setText(left);

        viewHolder.ivMenu.setImageResource(R.drawable.menu);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_CALL);
                i.setClass(context1, ViewTaskMenu.class);
                i.putExtra(Task.COLUMN_TITLE, cursor.getColumnIndex(Task.COLUMN_TITLE));
                i.putExtra(Task.COLUMN_DESC, cursor.getColumnIndex(Task.COLUMN_DESC));
                i.putExtra(Task.COLUMN_CREATIONDATE, cursor.getColumnIndex(Task.COLUMN_CREATIONDATE));
                i.putExtra(Task.COLUMN_DUEDATE, cursor.getColumnIndex(Task.COLUMN_DUEDATE));
                i.putExtra(Task.COLUMN_RECURR, cursor.getColumnIndex(Task.COLUMN_RECURR));
                i.putExtra(Task.COLUMN_CAT, cursor.getColumnIndex(Task.COLUMN_CAT));
                i.putExtra(Task.COLUMN_ID, cursor.getColumnIndex(Task.COLUMN_ID));
                //TODO for starting an intent
                //startActivity(context1, i);

            }
        });

    }

    public interface OnItemClickListener {
        public void onItemClick(Task t);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
