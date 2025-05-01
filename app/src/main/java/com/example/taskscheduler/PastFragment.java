package com.example.taskscheduler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PastFragment extends Fragment {


    RecyclerView rvPastTasks;
    TaskAdapter taskAdapter;
    TasksDao tasksDao;
    ArrayList<TaskModel> taskModelArrayList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvPastTasks=view.findViewById(R.id.rvPastTasks);
        rvPastTasks.setLayoutManager(new LinearLayoutManager(getContext()));

        tasksDao=new TasksDao(getContext());
        taskModelArrayList=tasksDao.getAllTasks();

        taskAdapter=new TaskAdapter(getContext(),taskModelArrayList);
        rvPastTasks.setAdapter(taskAdapter);

    }
}