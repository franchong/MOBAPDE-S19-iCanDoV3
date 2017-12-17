package edu.dlsu.mobapde.icandov3;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
                .inflate(R.layout.item, parent, false);
        return new TaskViewHolder(v);
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView tvLeft, tvRight;

        public TaskViewHolder(View itemView) {
            super(itemView);
            tvLeft = itemView.findViewById(R.id.tv_left);
            tvRight = itemView.findViewById(R.id.tv_right);
        }
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder viewHolder, final Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(Task.COLUMN_ID));
        String left = cursor.getString(cursor.getColumnIndex(Task.COLUMN_TITLE));
        viewHolder.tvLeft.setText(left);
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