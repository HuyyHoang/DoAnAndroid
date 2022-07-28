package com.example.doancuoiky.Screen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.Login.Login;
import com.example.doancuoiky.R;

public class LaunchScreen extends AppCompatActivity {
    private static int Splash_Time_out = 4500;
    Animation topAim, bottomAim;
    ImageView image;
    TextView text;

    SharedPreferences sharedPreferences_4 ;
    private final static String SHARED_PREF_NAME_4 = "ENTERED";
    private String KEY_ENTER;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.launchscreen);

        topAim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        image = findViewById(R.id.imageView_launchscreen);
        text = findViewById(R.id.textview_launchscreen);

        image.setAnimation(topAim);
        text.setAnimation(bottomAim);


        sharedPreferences_4 = getSharedPreferences(SHARED_PREF_NAME_4,MODE_PRIVATE);
        key = sharedPreferences_4.getString(KEY_ENTER, null);

        if(key == null) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LaunchScreen.this, activity_introduction.class );
                    startActivity(intent);
                    finish();
                }
            }, Splash_Time_out);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(LaunchScreen.this, Login.class );
                    startActivity(intent);
                    finish();
                }
            }, Splash_Time_out);
        }
    }

}
