package edu.dlsu.mobapde.icandov3;

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

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    ArrayList<Task> data;

    public TaskAdapter(ArrayList<Task> data) {
        this.data = data;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TaskViewHolder holder, int position) {
        final Task currentTask = data.get(position);
        holder.ivMenu.setImageResource(currentTask.getIcon());
        holder.tvLeft.setText(currentTask.getTitle());
        holder.tvRight.setText(currentTask.getDaysLeft() + " days left");

        holder.itemView.setTag(currentTask);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Task t = (Task) view.getTag();
                onItemClickListener.onItemClick(t);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
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

    public interface OnItemClickListener {
        public void onItemClick(Task t);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
