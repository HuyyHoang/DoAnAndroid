package com.example.doancuoiky.Story;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.doancuoiky.R;
import com.example.doancuoiky.Report.activity_report;

public class story3 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_story3, container, false);
        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_see_full_detail);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), activity_report.class);
                startActivity(intent);
            }
        });
        return view;
    }
}