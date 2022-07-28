package com.example.doancuoiky.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_create_account_begin extends Fragment implements AdapterView.OnItemSelectedListener {
    SharedPreferences sharedPreferences_2, sharedPreferences_3;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_USERNAME, KEY_ACCOUNT;
    private Spinner name_bank;
    private EditText et_money;
    private Button btn_continue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account_begin, container, false);

        et_money = (EditText) view.findViewById(R.id.et_balance);

        name_bank = (Spinner) view.findViewById(R.id.spinner_account_type);
        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bank_name, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name_bank.setAdapter(myAdapter);
        name_bank.setOnItemSelectedListener(this);

        btn_continue = (Button) view.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount(view);
            }
        });


        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void createAccount(View view) {
        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences_2.getString(KEY_USERNAME, null);

        String sTextFromET = et_money.getText().toString();
        int nIntFromET = new Integer(sTextFromET).intValue();

        CreateRequest create_req = new CreateRequest(value, name_bank.getSelectedItem().toString(), nIntFromET);

        Call<CreateResponse> create_accountCall = ApiClient.getUserService().create_accountCall(create_req);
        create_accountCall.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                if (response.isSuccessful()) {
                    sharedPreferences_3 = getActivity().getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences_3.edit();
                    editor.putString(KEY_ACCOUNT, name_bank.getSelectedItem().toString());
                    editor.apply();

                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_full, new fragment_create_account_success())
                            .commit();
                }
                else {
                    String mess = response.body().getMessage();
                    TextView text = (TextView) view.findViewById(R.id.tv_notify_create_account_begin);
                    text.setText(mess);
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}
