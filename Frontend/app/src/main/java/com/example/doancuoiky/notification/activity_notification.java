package com.example.doancuoiky.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.doancuoiky.R;
import com.example.doancuoiky.Profile.fragment_setting;
import com.example.doancuoiky.transaction.LocaleHelper;

import java.text.DateFormat;
import java.util.Calendar;

public class activity_notification extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private TextView mtextview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        TextView notification = (TextView) findViewById(R.id.textview_time_picker_notification);
        Button set_time = (Button) findViewById(R.id.button_set_time_notification);
        Button cancel_alarm = (Button) findViewById(R.id.button_cancel_alarm_notification);

        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(activity_notification.this, LocaleHelper.getLanguage(this));
        resources = context.getResources();

        notification.setText(resources.getString(R.string.textview_time_picker_notification));
        set_time.setText(resources.getString(R.string.button_set_time_notification));
        cancel_alarm.setText(resources.getString(R.string.button_cancel_alarm_notification));

        mtextview = findViewById(R.id.textview_time_picker_notification);

        Button button =(Button) findViewById(R.id.button_set_time_notification);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });

        Button buttonCancelAlarm = findViewById(R.id.button_cancel_alarm_notification);
        buttonCancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelAlarm();
            }
        });

        ImageButton btn_back_notification = (ImageButton) findViewById(R.id.btn_back_notification);
        btn_back_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_notification.this, fragment_setting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_the_right);
            }
        });


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
        StartAlarm(c);
    }

    private void updateTimeText(Calendar c) {
        String timeText = "Notification set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        mtextview.setText(timeText);

    }

    private void StartAlarm(Calendar c) {
        AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,intent, 0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager =(AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,intent, 0);

        alarmManager.cancel(pendingIntent);
        mtextview.setText("Notification canceled");

    }

   
}