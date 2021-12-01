package com.example.rember;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerVAdapter extends RecyclerView.Adapter<RecyclerVAdapter.RecyclerVHolder> {
    Context context;
    List<Task> tasks;

    public RecyclerVAdapter(List<Task> list, Context con) {
        this.tasks = list;
        this.context = con;
    }

    public class RecyclerVHolder extends RecyclerView.ViewHolder {
        ImageView email_provider;
        TextView task_name, task_desc;

        public RecyclerVHolder(@NonNull View itemView) {
            super(itemView);
            email_provider = itemView.findViewById(R.id.email_provider);
            task_name = itemView.findViewById(R.id.task_name);
            task_desc = itemView.findViewById(R.id.task_desc);
        }
    }

    @NonNull
    @Override
    public RecyclerVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_task_iitem, parent, false);
        return new RecyclerVHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerVHolder holder, int position) {
        holder.task_name.setText(tasks.get(position).name);
        holder.task_desc.setText(tasks.get(position).description);
    }

    @Override
    public int getItemCount() { return tasks.size(); }
}
