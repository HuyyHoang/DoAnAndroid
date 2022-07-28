package com.example.doancuoiky.ResetPass;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.Login.Login;
import com.example.doancuoiky.R;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class reset_password extends AppCompatActivity {

    SharedPreferences sharedPreferences_2, sharedPreferences_3;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_USERNAME, KEY_ACCOUNT, user, account;

    TextView tv_reset_password;

    EditText edit_oldpass, edit_newpass, edit_againpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password);

        Button btn_touch = (Button) findViewById(R.id.btn_continue_reset_password);
        ImageButton btn_back_reset_password = (ImageButton) findViewById(R.id.btn_back_reset_password);
        tv_reset_password = findViewById(R.id.tv_reset_password);

        sharedPreferences_2 = getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        sharedPreferences_3 = getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);

        user = sharedPreferences_2.getString(KEY_USERNAME, null);
        account = sharedPreferences_2.getString(KEY_USERNAME, null);

        edit_oldpass = findViewById(R.id.edit_oldpass);
        edit_newpass = findViewById(R.id.edit_newpass);
        edit_againpass = findViewById(R.id.edit_againpass);

        btn_touch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        btn_back_reset_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(reset_password.this, Login.class));
            }
        });

    }

    public void reset() {
        ChangePassRequest changePassRequest = new ChangePassRequest();

        changePassRequest.setUsername(user);
        changePassRequest.setOldPassword(edit_oldpass.getText().toString());
        changePassRequest.setNewPassword(edit_newpass.getText().toString());

        Call<ChangePassResponse> verifyCall = ApiClient.getUserService().verifyCall(changePassRequest);
        verifyCall.enqueue(new Callback<ChangePassResponse>() {
            @Override
            public void onResponse(Call<ChangePassResponse> call, Response<ChangePassResponse> response) {

                if (response.code() == 210 || response.code() == 220) {
                    tv_reset_password.setText(response.body().getMessage());
                }
                else if (!validatePassword()) {
                    return;
                }

                else if (response.code() == 200) {
                    Call<ChangePassResponse> changeCall = ApiClient.getUserService().changeCall(changePassRequest);
                    changeCall.enqueue(new Callback<ChangePassResponse>() {
                        @Override
                        public void onResponse(Call<ChangePassResponse> call, Response<ChangePassResponse> response) {
                            if (response.isSuccessful()) {
                                Intent intent = new Intent(reset_password.this, Login.class);
                                startActivity(intent);
                            }

                                Intent intent = new Intent(reset_password.this, Login.class);
                                startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<ChangePassResponse> call, Throwable t) {

                        }
                    });


                }

                }

            @Override
            public void onFailure(Call<ChangePassResponse> call, Throwable t) {

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
                    ".{8,}" +               //at least 8 characters
                    "$");





    private boolean validatePassword() {
        String passwordInput = edit_newpass.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            edit_newpass.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            edit_newpass.setError("Password too weak");
            return false;
        } else {
            edit_newpass.setError(null);
            return true;
        }
    }
}
