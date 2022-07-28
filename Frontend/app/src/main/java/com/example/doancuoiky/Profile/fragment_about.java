package com.example.doancuoiky.Profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.doancuoiky.R;


public class fragment_about extends AppCompatActivity {

    Animation topAim, bottomAim, leftAim, rightAim;
    LinearLayout hoang,hung,hai;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_about);

        topAim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        leftAim = AnimationUtils.loadAnimation(this, R.anim.left_animation);
        rightAim = AnimationUtils.loadAnimation(this, R.anim.right_animation);

        hoang = findViewById(R.id.hoang);
        hung = findViewById(R.id.hung);
        hai = findViewById(R.id.hai);

        hoang.setAnimation(leftAim);
        hung.setAnimation(rightAim);
        hai.setAnimation(leftAim);

        ImageView btn_back = (ImageView) findViewById(R.id.btn_back_about);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_about.this, fragment_setting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_the_right);
            }
        });
    }
}

