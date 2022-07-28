package com.example.doancuoiky.SignUp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.Login.Login;
import com.example.doancuoiky.R;
import com.example.doancuoiky.Login.UserRequest;
import com.example.doancuoiky.Login.UserResponse;
import com.example.doancuoiky.transaction.LocaleHelper;
import com.google.android.material.textfield.TextInputEditText;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    private TextInputEditText name, pass, email;
    private Button btnSignup, btnLogin;
    private RadioButton radioBtn;
    private TextView tv_notify_signup;

    SharedPreferences sharedPreferences_4, sharedPreferences_2;
    private final static String SHARED_PREF_NAME_4 = "EMAIL";
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private String KEY_EMAIL, KEY_USERNAME;
    String emailPattern = "[._]+@[a-z]+\\.+[a-z]+";
    String emailPattern1 = "[._]+[a-z]+\\.+[a-z]+";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        TextView title = (TextView) findViewById(R.id.title_Signup);
        TextView DieuKhoan = (TextView) findViewById(R.id.DieuKhoan);
        TextView btn_signup = (TextView) findViewById(R.id.btn_SignUp);
        TextView textView_have_account = (TextView) findViewById(R.id.dont_have_account);
        TextView button_log_in = (TextView) findViewById(R.id.btn_signup_login);
        tv_notify_signup = (TextView) findViewById(R.id.tv_notify_signup);
        radioBtn = findViewById(R.id.DieuKhoan);

        sharedPreferences_4 = getSharedPreferences(SHARED_PREF_NAME_4, MODE_PRIVATE);
        sharedPreferences_2 = getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);

        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(SignUp.this, LocaleHelper.getLanguage(this));
        resources = context.getResources();

        title.setText(resources.getString(R.string.Sign_up));
        DieuKhoan.setText(resources.getString(R.string.DieuKhoan));
        btn_signup.setText(resources.getString(R.string.Button_SignUp));
        textView_have_account.setText(resources.getString(R.string.textview_have_account));
        button_log_in.setText(resources.getString(R.string.Button_Log_in2));

        ImageButton btn_back_signup = (ImageButton) findViewById(R.id.btn_back_signup);
        btn_back_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_the_right);
            }
        });

        name = findViewById(R.id.signup_name);
        pass = findViewById(R.id.signup_pass);
        email = findViewById(R.id.signup_email);
        btnSignup = findViewById(R.id.btn_SignUp);
        btnLogin = findViewById(R.id.btn_signup_login);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (!validateEmail() | !validateUsername() | !validatePassword()) {
                        return;
                    }
                    if (radioBtn.isChecked()){
                        saveUser(createRequest());
                    } else {
                        tv_notify_signup.setText("Please agree the term to sign up");
                    }
            }

        });

    }

    public UserRequest createRequest(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(name.getText().toString());
        userRequest.setPassword(pass.getText().toString());
        userRequest.setEmail(email.getText().toString());
        return userRequest;
    }

    public void saveUser(UserRequest userRequest){
        Call<UserResponse> userResponseCall = ApiClient.getUserService().saveUser(userRequest);
        userResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if(response.code() == 210 || response.code() == 220) {
                    String message = response.body().getMessage();
                    tv_notify_signup.setText(message);
                }

                else if (response.code() == 200){
                    SharedPreferences.Editor editor4 = sharedPreferences_4.edit();
                    editor4.putString(KEY_EMAIL, email.getText().toString());
                    editor4.apply();

                    SharedPreferences.Editor editor2 = sharedPreferences_2.edit();
                    editor2.putString(KEY_USERNAME, name.getText().toString());
                    editor2.apply();

                    Intent intent = new Intent(SignUp.this, VerifyEmailSignUp.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }


    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=!])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address.");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean validateUsername() {
        String usernameInput = name.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            name.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            name.setError("Username too long");
            return false;
        }
        else {
            name.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String passwordInput = pass.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            pass.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pass.setError("Password too weak. Password must at least  have at least 1 digit, 1 lower case letter, 1 upper case letter, 1 special character,no white spaces and 8 characters");
            return false;
        } else {
            pass.setError(null);
            return true;
        }
    }




}