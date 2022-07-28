package com.example.doancuoiky.SignUp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.Login.Login;
import com.example.doancuoiky.OTP.CreateOTPRequest;
import com.example.doancuoiky.OTP.CreateOTPResponse;
import com.example.doancuoiky.R;
import com.example.doancuoiky.forgotpassword.ForgotRequest;
import com.example.doancuoiky.forgotpassword.ForgotResponse;
import com.example.doancuoiky.transaction.LocaleHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyEmailSignUp extends AppCompatActivity {

    private EditText inputcode1, inputcode2, inputcode3 ,inputcode4, inputcode5, inputcode6;
    private Button btn_verify;
    private String OTP;

    SharedPreferences sharedPreferences_4, sharedPreferences_2;
    private final static String SHARED_PREF_NAME_4 = "EMAIL";
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private String KEY_EMAIL, KEY_USERNAME, user, email;

    TextView tv_notify_verify;
    Button text_resend_otp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_2);

        inputcode1 = findViewById(R.id.forgot_pass_input_c1);
        inputcode2 = findViewById(R.id.forgot_pass_input_c2);
        inputcode3 = findViewById(R.id.forgot_pass_input_c3);
        inputcode4 = findViewById(R.id.forgot_pass_input_c4);
        inputcode5 = findViewById(R.id.forgot_pass_input_c5);
        inputcode6 = findViewById(R.id.forgot_pass_input_c6);
        setupOTPInputs();


        sharedPreferences_4 = getSharedPreferences(SHARED_PREF_NAME_4, MODE_PRIVATE);
        email = sharedPreferences_4.getString(KEY_EMAIL, null);

        sharedPreferences_2 = getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        user = sharedPreferences_2.getString(KEY_USERNAME, null);

        createOTP();

        TextView textview2 = (TextView) findViewById(R.id.textView2);
        TextView textview3 = (TextView) findViewById(R.id.textView3);
        TextView textview4 = (TextView) findViewById(R.id.textView4);
        TextView textview5 = (TextView) findViewById(R.id.text_resend_otp);
        tv_notify_verify = (TextView) findViewById(R.id.tv_notify_verify);
        text_resend_otp = (Button) findViewById(R.id.text_resend_otp);

        btn_verify = (Button) findViewById(R.id.btn_verify);
        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(VerifyEmailSignUp.this, LocaleHelper.getLanguage(this));
        resources = context.getResources();

        textview2.setText(resources.getString(R.string.textview2));
        textview3.setText(resources.getString(R.string.textview3));
        textview4.setText(resources.getString(R.string.textview4));
        textview5.setText(resources.getString(R.string.text_resend_otp));
        btn_verify.setText(resources.getString(R.string.btn_verify));

        btn_verify = (Button) findViewById(R.id.btn_verify);
        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OTP = inputcode1.getText().toString() + inputcode2.getText().toString() + inputcode3.getText().toString() + inputcode4.getText().toString() + inputcode5.getText().toString() + inputcode6.getText().toString() ;
                sendOTP(OTP);
            }
        });

        text_resend_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOTP();

            }
        });

    }

    private void setupOTPInputs(){
        inputcode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputcode2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputcode3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputcode4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputcode5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputcode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    inputcode6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void createOTP() {
        CreateOTPRequest createRequest = new CreateOTPRequest(user);
        Call<CreateOTPResponse> createOTPResponseCall = ApiClient.getUserService().createOTPCall(createRequest);
        createOTPResponseCall.enqueue(new Callback<CreateOTPResponse>() {
            @Override
            public void onResponse(Call<CreateOTPResponse> call, Response<CreateOTPResponse> response) {
                if (response.isSuccessful()) {
                    Log.d("message", response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<CreateOTPResponse> call, Throwable t) {

            }
        });
    }

    private void sendOTP(String OTP) {
        ForgotRequest forgotRequest = new ForgotRequest();
        forgotRequest.setUsername(user);
        forgotRequest.setEmail(email);
        forgotRequest.setOtp(OTP);


        Call<ForgotResponse> resetCall = ApiClient.getUserService().resetCall(forgotRequest);
        resetCall.enqueue(new Callback<ForgotResponse>() {
            @Override
            public void onResponse(Call<ForgotResponse> call, Response<ForgotResponse> response) {
                if (response.code() == 220 || response.code() == 230 || response.code() == 240) {
                    tv_notify_verify.setText(response.body().getMessage());
                }
                else if (response.code() == 200) {
                    startActivity(new Intent(VerifyEmailSignUp.this, Login.class));
                }
            }

            @Override
            public void onFailure(Call<ForgotResponse> call, Throwable t) {
                Toast.makeText(VerifyEmailSignUp.this, "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}