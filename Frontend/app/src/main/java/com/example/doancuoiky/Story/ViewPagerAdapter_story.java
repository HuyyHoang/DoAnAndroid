package com.example.doancuoiky.Story;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

class ViewPagerAdapter_story extends FragmentStateAdapter {
    public ViewPagerAdapter_story(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 1:
                return new story2();
            case 2:
                return new story3();
            case 3:
                return new story1();
        }

        return new story1();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
