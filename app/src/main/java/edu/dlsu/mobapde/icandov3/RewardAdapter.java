package edu.dlsu.mobapde.icandov3;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
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

    public class RewardViewHolder extends RecyclerView.ViewHolder {
        TextView tvLeft, tvRight;

        public RewardViewHolder(View itemView) {
            super(itemView);
            tvLeft = itemView.findViewById(R.id.tv_left);
            tvRight = itemView.findViewById(R.id.tv_right);
        }
    }

    @Override
    public void onBindViewHolder(final RewardViewHolder viewHolder, final Cursor cursor) {

        long id = cursor.getLong(cursor.getColumnIndex(Reward.COLUMN_ID));
        String name = cursor.getString(cursor.getColumnIndex(Reward.COLUMN_TITLE));
        int points = cursor.getInt(cursor.getColumnIndex(Reward.COLUMN_POINTS));

        viewHolder.tvLeft.setText(name);
        viewHolder.tvRight.setText(points + " Points");
        viewHolder.itemView.setTag(id);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick((Long) v.getTag());
            }
        });

    }

    public interface OnItemClickListener {
        public void onItemClick(Long r);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}
