package com.example.taskscheduler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private ScheduleFragment scheduleFragment=new ScheduleFragment();
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return scheduleFragment;
            case 1:
                return new PastFragment();
            case 2:
                return new NotificationFragment();
            case 3:
                return new ProfileFragment();
            default:
                return new ScheduleFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public ScheduleFragment getScheduleFragment(){
        return scheduleFragment;
    }
}
