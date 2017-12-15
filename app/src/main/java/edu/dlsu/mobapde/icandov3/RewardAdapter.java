package edu.dlsu.mobapde.icandov3;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dell on 12/09/2017.
 */

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {

    ArrayList<Reward> data;

    public RewardAdapter(ArrayList<Reward> data) {
        this.data = data;
    }

    @Override
    public RewardAdapter.RewardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new RewardAdapter.RewardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RewardViewHolder holder, int position) {
        final Reward currentReward = data.get(position);
        holder.tvLeft.setText(currentReward.getTitle());
        holder.tvRight.setText(currentReward.getPoints() + " points");

        holder.itemView.setTag(currentReward);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reward r = (Reward) view.getTag();
                onItemClickListener.onItemClick(r);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
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

    private RewardAdapter.OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(RewardAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
