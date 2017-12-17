package edu.dlsu.mobapde.icandov3;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dell on 12/08/2017.
 */

public class CategoryAdapter extends CursorRecyclerViewAdapter <CategoryAdapter.CategoryViewHolder> {
    private OnItemClickListener onItemClickListener;

    public CategoryAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new CategoryViewHolder(v);
    }
    /*  long id = cursor.getLong(cursor.getColumnIndex(Reminder.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(Reminder.COLUMN_REMINDER));
        int p = cursor.getInt(cursor.getColumnIndex(Reminder.COLUMN_PRIORITY));
        Reminder reminder = new Reminder();
        reminder.setId(id);
        reminder.setReminder(name);
        reminder.setPriority(p);
     */
    @Override
    public void onBindViewHolder(CategoryViewHolder viewHolder, Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndex(Category.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(Category.COLUMN_NAME));
        //viewHolder.ivMenu.setImageResource();
        viewHolder.tvLeft.setText(name);

        viewHolder.itemView.setTag(id);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pass id to caller
                onItemClickListener.onItemClick((Long) v.getTag());
            }
        });
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView tvLeft, tvRight;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvLeft = itemView.findViewById(R.id.tv_left);
            tvRight = itemView.findViewById(R.id.tv_right);
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(Long c);
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
