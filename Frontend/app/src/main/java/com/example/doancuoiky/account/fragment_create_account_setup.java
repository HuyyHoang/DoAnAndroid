package com.example.doancuoiky.account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doancuoiky.R;

public class fragment_create_account_setup extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_setup, container, false);

        Button btn_letgo = (Button) view.findViewById(R.id.btn_letgo);
        btn_letgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_from_right,  // enter
                                R.anim.slide_out_to_the_left // exit
                        )
                        .replace(R.id.container_full, new fragment_create_account_begin())
                        .commit();
            }
        });
        return view;
    }
}
