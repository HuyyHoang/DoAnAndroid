package com.example.doancuoiky.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_create_account extends Fragment implements AdapterView.OnItemSelectedListener {
    SharedPreferences sharedPreferences_2, sharedPreferences_3;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_USERNAME, KEY_ACCOUNT;
    private Spinner name_bank;
    private ArrayList<ItemAccountResponse> array;
    private EditText et_account_money;
    private TextView text;
    private Button btn_continue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_account, container, false);
        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back_create_account);

        btn_continue = (Button) view.findViewById(R.id.btn_continue_create_account);
        text = (TextView) view.findViewById(R.id.tv_notify_create_account);

        et_account_money = (EditText)  view.findViewById(R.id.et_account_money);
        array = new ArrayList<ItemAccountResponse>();

        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences_2.getString(KEY_USERNAME, null);

        name_bank = (Spinner) view.findViewById(R.id.wallet_bank);
        getData(view);

        /*
        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bank_name, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name_bank.setAdapter(myAdapter);
        name_bank.setOnItemSelectedListener(this);

         */

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_from_left,  // enter
                                R.anim.slide_out_to_the_right // exit
                        )
                        .replace(R.id.container,new fragment_account())
                        .commit();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(view);
            }
        });
        return view ;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void getData(View view) {
        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences_2.getString(KEY_USERNAME, null);

        ItemAccountRequest item_req = new ItemAccountRequest(value);
        Call<List<ItemAccountResponse>> getlistNotExistAccount = ApiClient.getUserService().getlistNotExistAccount(item_req);
        getlistNotExistAccount.enqueue(new Callback<List<ItemAccountResponse>>() {
            @Override
            public void onResponse(Call<List<ItemAccountResponse>> call, Response<List<ItemAccountResponse>> response) {
                if (response.isSuccessful()) {
                    List<ItemAccountResponse> items = response.body();
                    Log.d("size----------------","" + items.size());
                    if (items.size() == 0) {
                        btn_continue.setClickable(false);
                    }
                    else {
                        btn_continue.setClickable(true);
                        for (ItemAccountResponse item: items) {
                            array.add(new ItemAccountResponse(item.getName()));
                        }

                        ArrayAdapter<ItemAccountResponse> spinAccountAdapter = new ArrayAdapter<ItemAccountResponse>(getActivity(), android.R.layout.simple_spinner_item, array);
                        spinAccountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        name_bank.setAdapter(spinAccountAdapter);
                        name_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                }
            }

            @Override
            public void onFailure(Call<List<ItemAccountResponse>> call, Throwable t) {
            }
        });
    }

    public void createAccount(View view) {
        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences_2.getString(KEY_USERNAME, null);

        String sTextFromET = et_account_money.getText().toString();
        int nIntFromET = new Integer(sTextFromET).intValue();

        CreateRequest create_req = new CreateRequest(value, name_bank.getSelectedItem().toString(), nIntFromET);

        Call<CreateResponse> create_accountCall = ApiClient.getUserService().create_accountCall(create_req);
        create_accountCall.enqueue(new Callback<CreateResponse>() {
            @Override
            public void onResponse(Call<CreateResponse> call, Response<CreateResponse> response) {
                if (response.isSuccessful()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, new fragment_account())
                            .commit();
                }
                else {
                    String mess = response.body().getMessage();
                    TextView text = (TextView) view.findViewById(R.id.tv_notify_create_account);
                    text.setText(mess);
                }
            }

            @Override
            public void onFailure(Call<CreateResponse> call, Throwable t) {

            }
        });
    }
}