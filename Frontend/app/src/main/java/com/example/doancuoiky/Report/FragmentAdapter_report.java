package com.example.doancuoiky.Report;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.doancuoiky.Report.fragment_report_1;
import com.example.doancuoiky.Report.fragment_report_2;

public class FragmentAdapter_report extends FragmentStateAdapter {
    public FragmentAdapter_report(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position)
        {
            case 1:
                return new fragment_report_2();
        }

        return new fragment_report_1();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
