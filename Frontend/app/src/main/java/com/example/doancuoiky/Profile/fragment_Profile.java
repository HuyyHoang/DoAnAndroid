package com.example.doancuoiky.Profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.doancuoiky.Screen.LaunchScreen;
import com.example.doancuoiky.R;
import com.example.doancuoiky.account.fragment_account;
import com.example.doancuoiky.transaction.LocaleHelper;


public class fragment_Profile extends Fragment {
    public static final String TAG = fragment_Profile.class.getName();
    private TextView textViewName;
    private Button buttonChange;

    private LinearLayout btn_account, btn_setting, btn_export, btn_logout;
    private ImageButton btn_back_account;

    SharedPreferences sharedPreferences, sharedPreferences_2, sharedPreferences_3;
    private final static String SHARED_PREF_NAME = "MYPREF";
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_USERNAME;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        textViewName = (TextView) view.findViewById(R.id.textview_Name);
        btn_account = (LinearLayout) view.findViewById(R.id.btn_account);
        btn_setting = (LinearLayout) view.findViewById(R.id.btn_setting);
        btn_export = (LinearLayout) view.findViewById(R.id.btn_export);
        btn_logout = (LinearLayout) view.findViewById(R.id.btn_logout);
        btn_back_account = (ImageButton) view.findViewById(R.id.btn_back_account);

        TextView textView10 = (TextView) view.findViewById(R.id.textview10);
        TextView account = (TextView) view.findViewById(R.id.textview_account_profile);
        TextView setting = (TextView) view.findViewById(R.id.textview_account_setting);
        TextView export = (TextView) view.findViewById(R.id.textview_account_export_data);
        TextView log_out = (TextView) view.findViewById(R.id.textview_log_out_profile);

        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(getActivity(), LocaleHelper.getLanguage(getActivity()));
        resources = context.getResources();

        textView10.setText(resources.getString(R.string.textview10));
        account.setText(resources.getString(R.string.textview_account_profile));
        setting.setText(resources.getString(R.string.textview_account_setting));
        export.setText(resources.getString(R.string.textview_account_export_data));
        log_out.setText(resources.getString(R.string.textview_log_out_profile));

        sharedPreferences_2 = getActivity().getSharedPreferences("MYPREF_2", Context.MODE_PRIVATE);
        textViewName.setText(sharedPreferences_2.getString(KEY_USERNAME, null));

        btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_from_right,  // enter
                                R.anim.slide_out_to_the_left // exit
                        )
                        .replace(R.id.container, new fragment_account())
                        .commit();
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_the_left);
            }
        });

        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), fragment_setting.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_the_left);
            }
        });

        btn_export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_from_right,  // enter
                                R.anim.slide_out_to_the_left // exit
                        )
                        .replace(R.id.container, new fragment_export())
                        .commit();
            }
        });

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDiaLog();
            }
        });
        Button btnchange = (Button) view.findViewById(R.id.button_change_information);
        btnchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_from_right,  // enter
                                R.anim.slide_out_to_the_left // exit
                        )
                        .addToBackStack(fragment_change_information.class.getName())
                        .replace(R.id.container, new fragment_change_information())
                        .commit();
            }
        });
        return view;
    }

    public void showAlertDiaLog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Confirm Log out");
        alertDialog.setMessage("Are you sure want to log out ?");
        alertDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                sharedPreferences = getActivity().getSharedPreferences("MYPREF", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                sharedPreferences_2 = getActivity().getSharedPreferences("MYPREF_2", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_2 = sharedPreferences_2.edit();
                editor_2.clear();
                editor_2.apply();

                sharedPreferences_3 = getActivity().getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_3 = sharedPreferences_3.edit();
                editor_3.clear();
                editor_3.apply();

                Intent intent = new Intent(getActivity(), LaunchScreen.class);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();

    }
}