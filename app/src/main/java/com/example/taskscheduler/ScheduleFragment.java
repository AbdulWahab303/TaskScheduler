package com.example.taskscheduler;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment implements MainActivity.NotifyData {



    RecyclerView rvScheduleList;

    CalendarView calendarView;
    ArrayList<TaskModel> taskModelArrayList;
    TasksDao tasksDao;
    TaskAdapter taskAdapter;

    TextView tvScheduleHeading;

    public ScheduleFragment(){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        tvScheduleHeading = view.findViewById(R.id.tvScheduleHeading);
        calendarView = view.findViewById(R.id.calendarView);
        tasksDao = new TasksDao(getContext());

        Calendar today = Calendar.getInstance();

        int year1 = today.get(Calendar.YEAR);
        int month1 = today.get(Calendar.MONTH) + 1; // remember Calendar.MONTH is 0-indexed
        int day1 = today.get(Calendar.DAY_OF_MONTH);

        String selectedDate = day1 + "/" + month1 + "/" + year1;
        try {
            calendarView.setDate(today);
            tvScheduleHeading.setText("Schedule for " + selectedDate);
        } catch (OutOfDateRangeException e) {
            throw new RuntimeException(e);
        }

        rvScheduleList = view.findViewById(R.id.rvScheduleList);
        rvScheduleList.setLayoutManager(new LinearLayoutManager(getContext()));

        taskModelArrayList = tasksDao.getAllTasks();
        taskAdapter = new TaskAdapter(getContext(), taskModelArrayList);
        rvScheduleList.setAdapter(taskAdapter);

        List<EventDay> eventDayList = new ArrayList<>();
        eventDayList.add(new EventDay(today,R.drawable.dot_for_calender));

        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy", Locale.getDefault());

        for (TaskModel task : taskModelArrayList) {
            String dateString = task.get_datetime();

            try {
                Date date = sdf.parse(dateString);
                Calendar taskCalendar = Calendar.getInstance();
                if (date != null) {
                    taskCalendar.setTime(date);

                    eventDayList.add(new EventDay(taskCalendar, R.drawable.dot_for_calendar_new_task));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        calendarView.setEvents(eventDayList);

        calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDayCalendar = eventDay.getCalendar();
            try {
                calendarView.setDate(clickedDayCalendar);
            } catch (OutOfDateRangeException e) {
                throw new RuntimeException(e);
            }

            int year = clickedDayCalendar.get(Calendar.YEAR);
            int month = clickedDayCalendar.get(Calendar.MONTH) + 1;
            int day = clickedDayCalendar.get(Calendar.DAY_OF_MONTH);

            String selectedDate1 = day + "/" + month + "/" + year;
            tvScheduleHeading.setText("Schedule for " + selectedDate1);
            taskModelArrayList.clear();
            ArrayList<TaskModel>future=new ArrayList<>();
            future.addAll(tasksDao.getAllTasks());
            for(TaskModel task:future){
                try {
                    Date taskDate=sdf.parse(task.get_datetime());
                    if(taskDate!=null && taskDate.equals(clickedDayCalendar.getTime())){
                        taskModelArrayList.add(task);
                    }
                }
                catch (ParseException e){
                    e.printStackTrace();
                }
            }
            taskAdapter.notifyDataSetChanged();
            Toast.makeText(getContext(), "Selected Date: " + selectedDate1, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void notifyItemAdded() {
        taskModelArrayList.clear();
        taskModelArrayList.addAll(tasksDao.getAllTasks());
        taskAdapter.notifyDataSetChanged();
    }
}