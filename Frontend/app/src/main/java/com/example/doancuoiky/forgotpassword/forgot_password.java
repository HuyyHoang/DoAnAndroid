package com.example.doancuoiky.forgotpassword;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.Login.Login;
import com.example.doancuoiky.OTP.CreateOTPRequest;
import com.example.doancuoiky.OTP.CreateOTPResponse;
import com.example.doancuoiky.R;
import com.example.doancuoiky.transaction.LocaleHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgot_password extends AppCompatActivity {
    EditText et_username;
    TextView tv_notify_forgot_1;

    SharedPreferences sharedPreferences_2;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private String KEY_USERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);


        TextView title = (TextView) findViewById(R.id.title_forgot_password);
        TextView dontworry2 = (TextView) findViewById(R.id.textview_dont_worry2);
        Button btn_continue = (Button) findViewById(R.id.btn_continue_forgot_password);

        et_username = (EditText) findViewById(R.id.et_username);
        tv_notify_forgot_1 = findViewById(R.id.tv_notify_forgot_1);

        sharedPreferences_2 = getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);

        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(forgot_password.this, LocaleHelper.getLanguage(this));
        resources = context.getResources();

        title.setText(resources.getString(R.string.title_forgot_password));
        dontworry2.setText(resources.getString(R.string.textview_dont_worry_2_forgot_password));
        btn_continue.setText(resources.getString(R.string.button_continue_forgot_password));

        ImageButton btn_touch = (ImageButton) findViewById(R.id.btn_back_forgot_password);
        btn_touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(forgot_password.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_the_right);

            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOTP();

            }
        });

    }

    private void createOTP(){
        CreateOTPRequest createRequest = new CreateOTPRequest(et_username.getText().toString().trim());
        Call<CreateOTPResponse> createOTPResponseCall = ApiClient.getUserService().createOTPCall(createRequest);
        createOTPResponseCall.enqueue(new Callback<CreateOTPResponse>() {
            @Override
            public void onResponse(Call<CreateOTPResponse> call, Response<CreateOTPResponse> response) {
                if (response.code() == 210) {
                    tv_notify_forgot_1.setText(response.body().getMessage());
                }
                else if (response.code() == 200) {
                    Log.d("message", response.body().getMessage());
                    SharedPreferences.Editor editor2 = sharedPreferences_2.edit();
                    editor2.putString(KEY_USERNAME, et_username.getText().toString());
                    editor2.apply();
                    startActivity(new Intent(forgot_password.this, forgot_password_2.class));

                }
            }

            @Override
            public void onFailure(Call<CreateOTPResponse> call, Throwable t) {

            }
        });
    }

}