package com.example.doancuoiky.Profile;

import static android.content.Context.MODE_PRIVATE;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.doancuoiky.Profile.fragment_Profile;
import com.example.doancuoiky.R;
import com.example.doancuoiky.ResetPass.reset_password;
import com.example.doancuoiky.transaction.LocaleHelper;
import com.google.android.material.textfield.TextInputEditText;

public class fragment_change_information extends Fragment {
    SharedPreferences sharedPreferences_2, sharedPreferences_3, sharedPreferences_4;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private final static String SHARED_PREF_NAME_4 = "EMAIL";
    private String KEY_USERNAME, KEY_ACCOUNT, KEY_EMAIL, user, account;

    TextInputEditText edittext_username_change_information, edittext_email_change_information;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_change_information, container, false);

        TextView title_change_information = (TextView) view.findViewById(R.id.title_change_information);
        TextView username = (TextView) view.findViewById(R.id.textview_username_change_information);
        TextView email = (TextView) view.findViewById(R.id.textview_email_change_information);
        TextView edittext_username = (TextView) view.findViewById(R.id.edittext_username_change_information);
        TextView edittext_email = (TextView) view.findViewById(R.id.edittext_email_change_information);
        Button reset_password_change_informtion = (Button) view.findViewById(R.id.btn_reset_change_information);

        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        sharedPreferences_3 = getActivity().getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
        sharedPreferences_4 = getActivity().getSharedPreferences(SHARED_PREF_NAME_4, MODE_PRIVATE);

        String user = sharedPreferences_2.getString(KEY_USERNAME, null);
        String account = sharedPreferences_3.getString(KEY_ACCOUNT, null);
        String email_name = sharedPreferences_4.getString(KEY_EMAIL, null);

        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(getActivity(), LocaleHelper.getLanguage(getActivity()));
        resources = context.getResources();

        title_change_information.setText(resources.getString(R.string.title_change_information));
        username.setText(resources.getString(R.string.textview_username_change_information));
        email.setText(resources.getString(R.string.textview_email_change_information));
        edittext_username.setText(resources.getString(R.string.textview_username_change_information));
        edittext_email.setText(resources.getString(R.string.textview_email_change_information));
        reset_password_change_informtion.setText(resources.getString(R.string.btn_reset_password_change_information));

        edittext_email.setText(email_name);
        edittext_username.setText(user);

        ImageButton btn_back= (ImageButton) view.findViewById(R.id.btn_back_change_information);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_from_left,  // enter
                                R.anim.slide_out_to_the_right // exit
                        )
                        .replace(R.id.container, new fragment_Profile())
                        .commit();
            }
        });

        Button btn_reset = (Button) view.findViewById(R.id.btn_reset_change_information);
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), reset_password.class));
            }
        });




        return view;
    }
}