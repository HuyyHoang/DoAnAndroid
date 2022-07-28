package com.example.doancuoiky.Screen;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.doancuoiky.Expense.fragment_create_expense;
import com.example.doancuoiky.Income.fragment_create_income;
import com.example.doancuoiky.Profile.fragment_Profile;
import com.example.doancuoiky.R;
import com.example.doancuoiky.budget.fragment_Budget;
import com.example.doancuoiky.Home.fragment_home;
import com.example.doancuoiky.transaction.fragment_transaction;
import com.example.doancuoiky.transfer.fragment_create_transfer;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainScreen extends AppCompatActivity {
    private String TAG_NAME_FRAGMENT;

    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    GoogleSignInClient mGoogleSignInClient;
    ImageView ava_home;
    private boolean clicked = false;

    SharedPreferences sharedPreferences_4;
    private final static String SHARED_PREF_NAME_4 = "ENTERED";
    private String KEY_ENTER;
    private String key;

    private FloatingActionButton btn_trans,btn_income, btn_expense, btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
        btn_trans = (FloatingActionButton) findViewById(R.id.btn_trans);
        btn_income = (FloatingActionButton) findViewById(R.id.btn_income);
        btn_expense = (FloatingActionButton) findViewById(R.id.btn_expense);

        rotateOpen = AnimationUtils.loadAnimation(this,R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(this,R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(this,R.anim.to_bottom_anim);

        sharedPreferences_4 = getSharedPreferences(SHARED_PREF_NAME_4,MODE_PRIVATE);
        key = sharedPreferences_4.getString(KEY_ENTER, null);

        if(key == null) {
            SharedPreferences.Editor editor4 = sharedPreferences_4.edit();
            editor4.putString(KEY_ENTER, "entered");
            editor4.apply();
        }

        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottomNav);
        bottomNav.setBackground(null);
        bottomNav.getMenu().getItem(2).setEnabled(false);

        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        }

        getSupportFragmentManager().beginTransaction().add(R.id.container, new fragment_home()).commit();
        bottomNav.setSelectedItemId(R.id.ic_home);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {

                    case R.id.ic_home:
                        fragment = new fragment_home();
                        TAG_NAME_FRAGMENT = fragment_home.TAG;
                        break;

                    case R.id.ic_trans:
                        fragment = new fragment_transaction();
                        TAG_NAME_FRAGMENT = fragment_transaction.TAG;
                        break;

                    case R.id.ic_budget:
                        fragment = new fragment_Budget();
                        TAG_NAME_FRAGMENT = fragment_Budget.TAG;
                        break;

                    case R.id.ic_profile:
                        fragment = new fragment_Profile();
                        TAG_NAME_FRAGMENT = fragment_Profile.TAG;
                        break;
                }
                if (clicked) {
                    onAddButtonClicked();
                }
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_from_right,  // enter
                                R.anim.slide_out_to_the_left // exit
                        )
                        .addToBackStack(TAG_NAME_FRAGMENT)
                        .replace(R.id.container, fragment)
                        .commit();
                return true;
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onAddButtonClicked();
            }
        });
        btn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new fragment_create_expense();
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.fade_in,  // enter
                                R.anim.fade_out // exit
                        )
                        .addToBackStack(fragment_create_income.class.getName())
                        .replace(R.id.container,fragment)
                        .commit();
                onAddButtonClicked();
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_the_left);
            }
        });
        btn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new fragment_create_income();
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.fade_in,  // enter
                                R.anim.fade_out // exit
                        )
                        .addToBackStack(fragment_create_income.class.getName())
                        .replace(R.id.container,fragment)
                        .commit();
                onAddButtonClicked();

            }
        });
        btn_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new fragment_create_transfer();
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.fade_in,  // enter
                                R.anim.fade_out// exit
                        )
                        .addToBackStack(fragment_create_transfer.class.getName())
                        .replace(R.id.container,fragment)
                        .commit();
                onAddButtonClicked();
            }
        });


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();
        }


    }

    private void onAddButtonClicked() {
        setVisibility(clicked);
        setAnimation(clicked);
        clicked = !clicked;
    }

    private void setVisibility(boolean clicked) {
        if (!clicked) {
            btn_income.setVisibility(View.VISIBLE);
            btn_trans.setVisibility(View.VISIBLE);
            btn_expense.setVisibility(View.VISIBLE);

            if (!btn_income.isClickable()) {
                btn_income.setClickable(true);
                btn_trans.setClickable(true);
                btn_expense.setClickable(true);
            }
        }
        else {
            btn_income.setVisibility(View.INVISIBLE);
            btn_trans.setVisibility(View.INVISIBLE);
            btn_expense.setVisibility(View.INVISIBLE);

            btn_income.setClickable(false);
            btn_trans.setClickable(false);
            btn_expense.setClickable(false);
        }
    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
            btn_income.startAnimation(fromBottom);
            btn_expense.startAnimation(fromBottom);
            btn_trans.startAnimation(fromBottom);
            btn_add.startAnimation(rotateOpen);
        }
        else {
            btn_income.startAnimation(toBottom);
            btn_expense.startAnimation(toBottom);
            btn_trans.startAnimation(toBottom);
            btn_add.startAnimation(rotateClose);
        }
    }




}