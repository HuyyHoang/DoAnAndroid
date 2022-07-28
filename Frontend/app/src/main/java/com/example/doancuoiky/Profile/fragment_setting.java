package com.example.doancuoiky.Profile;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.Screen.MainScreen;
import com.example.doancuoiky.R;
import com.example.doancuoiky.notification.activity_notification;
import com.example.doancuoiky.transaction.LocaleHelper;


public class fragment_setting extends AppCompatActivity {




    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_setting);


        ImageButton btn_back_setting = (ImageButton) findViewById(R.id.btn_back_setting);
        btn_back_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_setting.this, MainScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_the_right);
            }
        });

        LinearLayout btnabout = (LinearLayout) findViewById(R.id.btn_about);
        btnabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_setting.this, fragment_about.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_the_left);
            }
        });

        LinearLayout btnnotification = (LinearLayout) findViewById(R.id.linear_notification_setting);
        btnnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_setting.this, activity_notification.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_the_left);
            }
        });

        LinearLayout btnhelp = (LinearLayout) findViewById(R.id.linear_help_setting);
        btnhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_setting.this, fragment_contact_us.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_the_left);
            }
        });

        Spinner mLanguage = (Spinner) findViewById(R.id.spLanguage);
        //fragment_setting
        TextView title_setting = (TextView) findViewById(R.id.title_setting);
        TextView language_setting = (TextView) findViewById(R.id.language_setting);
        TextView notification_setting = (TextView) findViewById(R.id.notification_setting);
        TextView about_setting = (TextView) findViewById(R.id.about_setting);
        TextView help_setting = (TextView) findViewById(R.id.help_setting);


        ArrayAdapter mAdapter = new ArrayAdapter<String>(fragment_setting.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.language_option));
        mLanguage.setAdapter(mAdapter);

        if (LocaleHelper.getLanguage(fragment_setting.this).equalsIgnoreCase("en")) {
            mLanguage.setSelection(mAdapter.getPosition("English"));


        } else if (LocaleHelper.getLanguage(fragment_setting.this).equalsIgnoreCase("vi")) {
            mLanguage.setSelection(mAdapter.getPosition("VietNam"));

        }

        mLanguage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Context context;
                Resources resources;
                switch (i) {
                    case 0:
                        context = LocaleHelper.setLocale(fragment_setting.this, "en");
                        resources = context.getResources();

                        //fragment_setting
                        title_setting.setText(resources.getString(R.string.title_setting));
                        language_setting.setText(resources.getString(R.string.language_setting));
                        notification_setting.setText(resources.getString(R.string.notification_setting));
                        about_setting.setText(resources.getString(R.string.about_setting));
                        help_setting.setText(resources.getString(R.string.help_setting));

                        break;
                    case 1:
                        context = LocaleHelper.setLocale(fragment_setting.this, "vi");
                        resources = context.getResources();


                        //fragment_setting
                        title_setting.setText(resources.getString(R.string.title_setting));
                        language_setting.setText(resources.getString(R.string.language_setting));
                        notification_setting.setText(resources.getString(R.string.notification_setting));
                        about_setting.setText(resources.getString(R.string.about_setting));
                        help_setting.setText(resources.getString(R.string.help_setting));
                        break;


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

}