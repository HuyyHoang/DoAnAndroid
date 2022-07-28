package com.example.doancuoiky.Income;

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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.R;
import com.example.doancuoiky.account.ItemAccountRequest;
import com.example.doancuoiky.account.ItemAccountResponse;
import com.example.doancuoiky.Home.fragment_home;
import com.example.doancuoiky.transaction.CreateIncomeRequest;
import com.example.doancuoiky.transaction.CreateIncomeResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_create_income extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner name_bank, name_income;
    SharedPreferences sharedPreferences_2;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private String KEY_USERNAME;
    private EditText et_income_money;
    private EditText Description_Income;
    private Button btn_continue_Income;
    private ArrayList<ItemAccountResponse> array;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_income, container, false);
        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back_income);

        array = new ArrayList<ItemAccountResponse>();
        et_income_money = (EditText) view.findViewById(R.id.et_income_money);
        Description_Income = (EditText)  view.findViewById(R.id.Description_Income);
        btn_continue_Income = (Button) view.findViewById(R.id.btn_continue_Income);

        name_bank = (Spinner) view.findViewById(R.id.Wallet_Income);
        getNameBank(view);

        name_income = (Spinner) view.findViewById(R.id.Category_Income);
        ArrayAdapter<CharSequence> myAdapter_2 = ArrayAdapter.createFromResource(getActivity(), R.array.category_income, android.R.layout.simple_spinner_item);
        myAdapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name_income.setAdapter(myAdapter_2);
        name_income.setOnItemSelectedListener(this);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        btn_continue_Income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(et_income_money.getText().toString()) == 0) {
                    return;
                }
                createIncome();
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

    public void createIncome() {
        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences_2.getString(KEY_USERNAME, null);

        String sTextFromET = et_income_money.getText().toString();
        int amount = new Integer(sTextFromET).intValue();

        CreateIncomeRequest income_req = new CreateIncomeRequest
                (value, amount, Description_Income.getText().toString(), name_bank.getSelectedItem().toString(), name_income.getSelectedItem().toString());
        Call<CreateIncomeResponse> create_incomeCall = ApiClient.getUserService().create_incomeCall(income_req);
        create_incomeCall.enqueue(new Callback<CreateIncomeResponse>() {
            @Override
            public void onResponse(Call<CreateIncomeResponse> call, Response<CreateIncomeResponse> response) {
                if (response.isSuccessful()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(
                                    R.anim.fade_in,  // enter
                                    R.anim.fade_out // exit
                            )
                            .replace(R.id.container, new fragment_home())
                            .commit();
                }
            }

            @Override
            public void onFailure(Call<CreateIncomeResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getNameBank(View view) {
        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences_2.getString(KEY_USERNAME, null);

        ItemAccountRequest item_req = new ItemAccountRequest(value);
        Call<List<ItemAccountResponse>> getlistAccount = ApiClient.getUserService().getlistAccount(item_req);
        getlistAccount.enqueue(new Callback<List<ItemAccountResponse>>() {
            @Override
            public void onResponse(Call<List<ItemAccountResponse>> call, Response<List<ItemAccountResponse>> response) {
                if (response.isSuccessful()) {
                    List<ItemAccountResponse> items = response.body();
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

            @Override
            public void onFailure(Call<List<ItemAccountResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}