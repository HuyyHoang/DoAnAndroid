package com.example.doancuoiky.Expense;

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
import android.widget.TextView;

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


public class fragment_create_expense extends Fragment implements AdapterView.OnItemSelectedListener {
    private Spinner name_bank, name_expense;
    SharedPreferences sharedPreferences_2;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private String KEY_USERNAME;
    private EditText et_expense_money;
    private EditText Description_Expense;
    private Button btn_continue_Expense;
    private ArrayList<ItemAccountResponse> array;
    private TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_expense, container, false);
        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back_expense);
        text = (TextView) view.findViewById(R.id.tv_notify_create_expense) ;

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        array = new ArrayList<ItemAccountResponse>();
        et_expense_money = (EditText) view.findViewById(R.id.et_expense_money);
        Description_Expense = (EditText)  view.findViewById(R.id.Description_Expense);
        btn_continue_Expense = (Button) view.findViewById(R.id.btn_continue_Expense);
        name_bank = (Spinner) view.findViewById(R.id.Wallet_Expense);

        getNameBank(view);

        name_expense = (Spinner) view.findViewById(R.id.Category_Expense);
        ArrayAdapter<CharSequence> myAdapter_2 = ArrayAdapter.createFromResource(getActivity(), R.array.category_expense, android.R.layout.simple_spinner_item);
        myAdapter_2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name_expense.setAdapter(myAdapter_2);
        name_expense.setOnItemSelectedListener(this);

        btn_continue_Expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(et_expense_money.getText().toString()) == 0) {
                    return;
                }
                createExpense(v);
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

    public void  getNameBank(View view) {
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
            }
        });
    }

    public void createExpense(View view) {
        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences_2.getString(KEY_USERNAME, null);

        String sTextFromET = et_expense_money.getText().toString();
        int amount = new Integer(sTextFromET).intValue();

        CreateIncomeRequest income_req = new CreateIncomeRequest
                (value, amount, Description_Expense.getText().toString(), name_bank.getSelectedItem().toString(), name_expense.getSelectedItem().toString());
        Call<CreateIncomeResponse> create_expenseCall = ApiClient.getUserService().create_expenseCall(income_req);
        create_expenseCall.enqueue(new Callback<CreateIncomeResponse>() {
            @Override
            public void onResponse(Call<CreateIncomeResponse> call, Response<CreateIncomeResponse> response) {
                if(response.code()== 250) {
                    String mess = response.body().getMessage();
                    text.setText(mess);
                }
                else if (response.code() == 200) {
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
            }
        });
    }


}