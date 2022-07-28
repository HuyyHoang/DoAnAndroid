package com.example.doancuoiky.Screen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.doancuoiky.Login.Login;
import com.example.doancuoiky.Photo;
import com.example.doancuoiky.PhotoAdapter;
import com.example.doancuoiky.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class activity_introduction extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private PhotoAdapter photoAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);
        Button btn_back_signup = (Button) findViewById(R.id.btn_skip);

        btn_back_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_introduction.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_the_left);
            }
        });

        viewPager =findViewById(R.id.view_pager_introduction);
        circleIndicator =findViewById(R.id.circle_indicator_introduction);

        photoAdapter = new PhotoAdapter (this, getListPhoto());
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
    }

    private List<Photo> getListPhoto() {
        List <Photo> list = new ArrayList<>();
        list.add(new Photo (R.drawable.introduction1));
        list.add(new Photo (R.drawable.introduction2));
        list.add(new Photo (R.drawable.introduction3));

        return list;
    }

}