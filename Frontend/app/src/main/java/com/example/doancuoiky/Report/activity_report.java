package com.example.doancuoiky.Report;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.doancuoiky.Screen.MainScreen;
import com.example.doancuoiky.R;
import com.example.doancuoiky.transaction.LocaleHelper;
import com.google.android.material.tabs.TabLayout;

public class activity_report extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter_report adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        ImageView btnreport = (ImageView) findViewById(R.id.btn_back_report);
        btnreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_report.this, MainScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_the_right);
            }
        });

        tabLayout = findViewById(R.id.tab_layout);
        pager2 = findViewById(R.id.view_pager2);

        TextView title = (TextView) findViewById(R.id.title_activity_report);
        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(activity_report.this, LocaleHelper.getLanguage(this));
        resources = context.getResources();

        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter_report(fm, getLifecycle());
        pager2.setAdapter(adapter);

        title.setText(resources.getString(R.string.title_activity_report));
        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.tab_layout_expense)));
        tabLayout.addTab(tabLayout.newTab().setText(resources.getString(R.string.tab_layout_income)));

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