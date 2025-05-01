package com.example.taskscheduler;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {



    public interface NotifyData{
        void notifyItemAdded();
    }


    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter viewPagerAdapter;
    NotifyData scheduleFrag;

    FloatingActionButton fab;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();

        new TabLayoutMediator(
                tabLayout,
                viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        View customView= LayoutInflater.from(tabLayout.getContext()).inflate(R.layout.custom_tab_design,null);
                        TextView tvTabText=customView.findViewById(R.id.tab_text);
                        ImageView ivTabIcon=customView.findViewById(R.id.tab_icon);
                        switch (position){
                            case 0:
                                tvTabText.setText("Schedule");
                                ivTabIcon.setImageResource(R.drawable.calendar);
                                break;
                            case 1:
                                tvTabText.setText("Past");
                                ivTabIcon.setImageResource(R.drawable.schedule);
                                break;
                            case 2:
                                tvTabText.setText("Notification");
                                ivTabIcon.setImageResource(R.drawable.bell);
                                break;
                            case 3:
                                tvTabText.setText("Profile");
                                ivTabIcon.setImageResource(R.drawable.resume);
                                break;
                        }
                        tab.setCustomView(customView);
                    }
                }
        ).attach();


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view=tab.getCustomView();
                if(view!=null){
                    view.setBackgroundResource(R.drawable.tab_selected_background);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view=tab.getCustomView();
                if(view!=null){

                    view.setBackgroundResource(android.R.color.transparent);
                }

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });



        fab.setOnClickListener((v)->{
            addEvent();
        });
    }


    private void addEvent(){
//        AlertDialog.Builder builder=new AlertDialog.Builder(this);
//        builder.setTitle("Add New Event");
        View v= LayoutInflater.from(this).inflate(R.layout.custom_add_task_design,null,false);
//        builder.setView(v);
//        builder.create().show();
        EditText etName=v.findViewById(R.id.etEventName);
        EditText etEventNote=v.findViewById(R.id.etEventNote);
        EditText etDate=v.findViewById(R.id.etDate);
        Button btnCreate=v.findViewById(R.id.btnCreate);

        SwitchCompat switchCompat=v.findViewById(R.id.toggleSwitch);

        androidx.appcompat.app.AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        BottomSheetDialog bottomSheetDialog =new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(v);
        bottomSheetDialog.show();

        etDate.setOnClickListener((view)->{
            Calendar calendar=Calendar.getInstance();
            int year=calendar.get(Calendar.YEAR);
            int month=calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog=new DatePickerDialog(
                    MainActivity.this,
                    (v1,year1,monthOfYear,dayOfMonth)-> {
                        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year1;
                        etDate.setText(date);
                    },
                    year,month,day
            );
            datePickerDialog.show();
        });

        btnCreate.setOnClickListener((view)->{
            String title=etName.getText().toString().trim();
            String description=etEventNote.getText().toString().trim();
            String date=etDate.getText().toString();

            if(!title.isEmpty() && !description.isEmpty() && !date.isEmpty()){
                TasksDao db=new TasksDao(this);
                if(db.insert(title,description,date,0)>0){
                    Toast.makeText(this,"INSERTED IN DATABASE SUCCESSFULLY",Toast.LENGTH_LONG).show();
                    scheduleFrag.notifyItemAdded();
                    bottomSheetDialog.dismiss();
                }
            }
            else{
                Toast.makeText(this,"EMPTY FIELDS",Toast.LENGTH_LONG).show();
            }

        });


    }

    private void init(){
        tabLayout=findViewById(R.id.tabLayout);
        viewPager2=findViewById(R.id.viewPager2);
        fab=findViewById(R.id.fab);
        viewPagerAdapter=new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);
        scheduleFrag=(NotifyData) viewPagerAdapter.getScheduleFragment();
    }

}