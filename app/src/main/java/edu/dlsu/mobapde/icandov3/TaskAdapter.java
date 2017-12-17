package edu.dlsu.mobapde.icandov3;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by Dell on 12/08/2017.
 */

public class TaskAdapter extends CursorRecyclerViewAdapter<TaskAdapter.TaskViewHolder> {

    public TaskAdapter(Context context, Cursor cursor) {
        super(context, cursor);
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
    public void onBindViewHolder(final TaskViewHolder viewHolder, final Cursor cursor) {
        //TODO unsure Date data
        long id = cursor.getLong(cursor.getColumnIndex(Task.COLUMN_ID));
        String left = cursor.getString(cursor.getColumnIndex(Task.COLUMN_TITLE));
        viewHolder.tvLeft.setText(left);

        viewHolder.ivMenu.setImageResource(R.drawable.menu);
        viewHolder.itemView.setTag(id);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick((Long) v.getTag());
            }
        });

    }

    public interface OnItemClickListener {
        public void onItemClick(long r);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}