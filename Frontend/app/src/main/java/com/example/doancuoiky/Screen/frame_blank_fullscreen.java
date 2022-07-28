package com.example.doancuoiky.Screen;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.R;
import com.example.doancuoiky.account.fragment_create_account_setup;

public class frame_blank_fullscreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_blank_fullscreen);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_full, new fragment_create_account_setup())
                .commit();
    }
}
