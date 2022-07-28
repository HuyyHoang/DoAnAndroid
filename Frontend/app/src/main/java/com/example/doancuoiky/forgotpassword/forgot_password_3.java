package com.example.doancuoiky.forgotpassword;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.ResetPass.ChangePassRequest;
import com.example.doancuoiky.ResetPass.ChangePassResponse;
import com.example.doancuoiky.Login.Login;
import com.example.doancuoiky.R;
import com.example.doancuoiky.transaction.LocaleHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class forgot_password_3 extends AppCompatActivity {
    TextInputEditText newPass, reNewPass;
    TextInputEditText new_pass, retype_new_pass;
    TextView tv_notify_forgot;

    SharedPreferences sharedPreferences_2;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private String KEY_USERNAME, user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_3);

        TextView textView5 = (TextView) findViewById(R.id.textview5);
        newPass = findViewById(R.id.new_pass);
        reNewPass = findViewById(R.id.retype_new_pass);
        Button btn_continue = (Button) findViewById(R.id.btn_continue_forgot_password_3);

        tv_notify_forgot = findViewById(R.id.tv_notify_forgot);
        new_pass = findViewById(R.id.new_pass);
        retype_new_pass = findViewById(R.id.retype_new_pass);

        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(forgot_password_3.this, LocaleHelper.getLanguage(this));
        resources = context.getResources();

        textView5.setText(resources.getString(R.string.textview5));
        btn_continue.setText(resources.getString(R.string.btn_continue_forgot_password_3));

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!new_pass.getText().toString().trim().equals(retype_new_pass.getText().toString().trim())) {
                    tv_notify_forgot.setText("The retype password is not correct");
                }
                else if (!validatePassword()){

                }
                else changePass();
            }
        });
    }

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-24])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 4 characters
                    "$");

    private boolean validatePassword() {
        String passwordInput = newPass.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            tv_notify_forgot.setText("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            tv_notify_forgot.setText("Password too weak. Password must at least  have at least 1 digit, 1 lower case letter, 1 upper case letter, 1 special character,no white spaces and 8 characters");
            return false;
        } else {
            return true;
        }
    }
    public void changePass() {
        ChangePassRequest changePassRequest = new ChangePassRequest();
        sharedPreferences_2 = getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        user = sharedPreferences_2.getString(KEY_USERNAME, null);

        changePassRequest.setUsername(user);
        changePassRequest.setOldPassword(String.valueOf(retype_new_pass.getText()));
        changePassRequest.setNewPassword(String.valueOf(new_pass.getText()));

        Call<ChangePassResponse> changeCall = ApiClient.getUserService().changeCall(changePassRequest);
        changeCall.enqueue(new Callback<ChangePassResponse>() {
            @Override
            public void onResponse(Call<ChangePassResponse> call, Response<ChangePassResponse> response) {
                if (response.isSuccessful()) {
                    Intent intent = new Intent(forgot_password_3.this, Login.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ChangePassResponse> call, Throwable t) {
            }
        });
    }
}