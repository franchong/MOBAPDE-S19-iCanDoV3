package edu.dlsu.mobapde.icandov3;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Dell on 12/09/2017.
 */

public class RewardAdapter extends CursorRecyclerViewAdapter<RewardAdapter.RewardViewHolder> {


    public RewardAdapter(Context context, Cursor cursor) {
        super(context, cursor);
    }

    @Override
    public RewardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new RewardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RewardViewHolder viewHolder, Cursor cursor) {

        Reward reward = null;

        long id = cursor.getLong(cursor.getColumnIndex(Reward.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(Reward.COLUMN_TITLE));
        int points = cursor.getInt(cursor.getColumnIndex(Reward.COLUMN_POINTS));

        //viewHolder.ivMenu.setImageResource();
        viewHolder.tvLeft.setText(name);
        viewHolder.tvRight.setText(points + " Points");

        if (reward != null) {

            viewHolder.itemView.setTag(id);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick((Reward) v.getTag());
                }
            });

        }
    }

    public class RewardViewHolder extends RecyclerView.ViewHolder {

        TextView tvLeft;
        TextView tvRight;

        public RewardViewHolder(View itemView) {
            super(itemView);
            tvLeft = itemView.findViewById(R.id.tv_left);
            tvRight = itemView.findViewById(R.id.tv_right);
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(Reward r);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
