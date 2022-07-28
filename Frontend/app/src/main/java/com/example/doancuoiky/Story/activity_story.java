package com.example.doancuoiky.Story;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.doancuoiky.R;
import com.google.android.material.tabs.TabLayout;

public class activity_story extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    ViewPagerAdapter_story adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);


        tabLayout = findViewById(R.id.tab_layout_story);
        pager2 = findViewById(R.id.view_pager2_story);

        FragmentManager fm = getSupportFragmentManager();
        adapter = new ViewPagerAdapter_story(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText(" "));
        tabLayout.addTab(tabLayout.newTab().setText(" "));
        tabLayout.addTab(tabLayout.newTab().setText(" "));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }
}