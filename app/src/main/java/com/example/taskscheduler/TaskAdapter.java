package com.example.taskscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    Context context;
    private ArrayList<TaskModel> arrayList;
    public TaskAdapter(Context c, ArrayList<TaskModel>tasks){
        context=c;
        arrayList=tasks;

    }


    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view_design,parent,false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {

        TaskModel taskModel=arrayList.get(position);
        holder.tvTaskName.setText(taskModel.get_title());
        holder.tvTaskDescription.setText(taskModel.get_description());
        holder.tvTaskDate.setText(taskModel.get_datetime());
        if(taskModel.get_status()==1){
            holder.tvStatus.setText("Completed");
            holder.ivStatus.setImageResource(R.drawable.approved);
        }
        else{
            holder.tvStatus.setText("Pending");
            holder.ivStatus.setImageResource(R.drawable.pending);
        }


        holder.ivDelete.setOnClickListener((v)->{
            TasksDao tasksDao=new TasksDao(v.getContext());
            tasksDao.deleteTask(taskModel.get_id());
            int pos= holder.getAdapterPosition();
            if(pos!=RecyclerView.NO_POSITION){
                arrayList.remove(pos);
                notifyItemRemoved(pos);
            }

        });




    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{

        TextView tvTaskName,tvTaskDescription,tvTaskDate,tvStatus;
        ImageView ivTaskCategory,ivStatus,ivDelete;


        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTaskName=itemView.findViewById(R.id.tvTaskName);
            tvTaskDescription=itemView.findViewById(R.id.tvTaskDescription);
            tvTaskDate=itemView.findViewById(R.id.tvTaskDate);
            ivTaskCategory=itemView.findViewById(R.id.ivTaskCategory);
            ivStatus=itemView.findViewById(R.id.ivStatus);
            tvStatus=itemView.findViewById(R.id.tvStatus);
            ivDelete=itemView.findViewById(R.id.ivDelete);
        }
    }
}
