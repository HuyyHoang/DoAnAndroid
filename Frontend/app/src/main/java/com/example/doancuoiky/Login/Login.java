package com.example.doancuoiky.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.Screen.MainScreen;
import com.example.doancuoiky.R;
import com.example.doancuoiky.SignUp.SignUp;
import com.example.doancuoiky.account.activity_choose_account;
import com.example.doancuoiky.forgotpassword.forgot_password;
import com.example.doancuoiky.Screen.frame_blank_fullscreen;
import com.example.doancuoiky.transaction.LocaleHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.shobhitpuri.custombuttons.GoogleSignInButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    private TextInputEditText username, password;
    private TextInputLayout txt_input_username, txt_input_password;
    private Button btn_login, btn_forgot;

    GoogleSignInClient mGoogleSignInClient;
    private static String token;
    private String user_name;
    private static int RC_SIGN_IN=100;

    SharedPreferences sharedPreferences, sharedPreferences2, sharedPreferences_3, sharedPreferences_4;
    private final static String SHARED_PREF_NAME = "MYPREF";
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private final static String SHARED_PREF_NAME_4 = "EMAIL";

    private String KEY_USERNAME, KEY_TOKEN, KEY_ACCOUNT, KEY_EMAIL, name, message_login;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_input_username = (TextInputLayout) findViewById(R.id.txt_input_username);
        txt_input_password = (TextInputLayout) findViewById(R.id.txt_input_password);

        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        String value = sharedPreferences.getString(KEY_TOKEN, null);

        sharedPreferences2 = getSharedPreferences(SHARED_PREF_NAME_2,MODE_PRIVATE);

        sharedPreferences_3 = getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
        name = sharedPreferences_3.getString(KEY_ACCOUNT, null);

        sharedPreferences_4 = getSharedPreferences(SHARED_PREF_NAME_4, MODE_PRIVATE);

        TextView login = (TextView) findViewById(R.id.textview_Log_in);
        TextView textView = (TextView) findViewById(R.id.textView);
        TextView dont_have_acc = (TextView) findViewById(R.id.dont_have_acc);
        TextView button_forgot = (TextView) findViewById(R.id.btn_forgot);
        TextView button_login = (TextView) findViewById(R.id.btn_login);
        TextView button_signup = (TextView) findViewById(R.id.btn_signup);

        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(Login.this, LocaleHelper.getLanguage(this));
        resources = context.getResources();

        login.setText(resources.getString(R.string.login));
        textView.setText(resources.getString(R.string.Orwith));
        dont_have_acc.setText(resources.getString(R.string.dont_have_an_acc_yet));
        button_forgot.setText(resources.getString(R.string.forgot_password));
        button_login.setText(resources.getString(R.string.btn_login));
        button_signup.setText(resources.getString(R.string.btn_signup));

        if (value != null && name != null) {
            Log.d("value, name", value + " " + name);
            startActivity(new Intent(Login.this, MainScreen.class));
        }

        Button btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_the_left);
            }
        });

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {
                    txt_input_username.setHelperText("Username and password is required");
                }

                else if (false) {

                }

                else {
                    //Proceed to login
                    login();

                }
            }
        });

        btn_forgot = findViewById(R.id.btn_forgot);
        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInButton signInButton = findViewById(R.id.signin_with_gg);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        Button btn_forgot = (Button) findViewById(R.id.btn_forgot);
        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgot();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                Toast.makeText(Login.this,  "Login Successful", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(Login.this, MainScreen.class));

            // Signed in successfully, show authenticated UI.

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Failed",e.toString());

        }
    }

    public void login(){
        UserRequest userRequest = new UserRequest();
        userRequest.setUsername(username.getText().toString());
        userRequest.setPassword(password.getText().toString());

        Call<UserResponse> loginResponseCall = ApiClient.getUserService().loginCall(userRequest);
        loginResponseCall.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                if(response.code() == 230 || response.code() == 220) {
                    txt_input_username.setHelperText(response.body().getMessage());
                    txt_input_password.setHelperText(response.body().getMessage());
                }

                else if (response.code() == 200 || response.code() == 250){
                    token = response.body().getToken();
                    user_name = response.body().getUsername();
                    userRequest.setToken(token);
                    userRequest.setUsername(response.body().getUsername());
                    message_login = response.body().getMessage();

                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                    editor2.putString(KEY_USERNAME, user_name);
                    editor2.apply();

                    SharedPreferences.Editor editor4 = sharedPreferences_4.edit();
                    editor4.putString(KEY_EMAIL, response.body().getEmail());
                    editor4.apply();

                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    Call<UserResponse> accessResourcesCall = ApiClient.getUserService().getVerify(userRequest.getToken());
                    accessResourcesCall.enqueue(new Callback<UserResponse>() {
                        @Override
                        public void onResponse(Call<UserResponse> call, Response<UserResponse> responses) {
                            if (responses.isSuccessful()) {
                                editor.putString(KEY_TOKEN, token);
                                editor.apply();

                                if(message_login != null) {
                                    Log.d("message", message_login);
                                    startActivity(new Intent(Login.this, frame_blank_fullscreen.class));
                                    return;
                                }

                                startActivity(new Intent(Login.this, activity_choose_account.class));
                            }
                        }
                        @Override
                        public void onFailure(Call<UserResponse> call, Throwable t) {
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
            }
        });
    }

    public void forgot() {
        startActivity(new Intent(Login.this, forgot_password.class));
    }























}
