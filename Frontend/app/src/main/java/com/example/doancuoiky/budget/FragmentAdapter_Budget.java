package com.example.doancuoiky.budget;


import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.Fragment;

import com.example.doancuoiky.budget.fragment_Budget;

public class FragmentAdapter_Budget extends FragmentStatePagerAdapter {

    public FragmentAdapter_Budget( FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        fragment_Budget fragment = new fragment_Budget();
        Bundle bundle = new Bundle();
        i = i + 1;
        bundle.putString("MESSAGE","Thang" + i);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 12;
    }


}
