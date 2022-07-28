package com.example.doancuoiky.transfer;

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
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.R;
import com.example.doancuoiky.account.ItemAccountRequest;
import com.example.doancuoiky.account.ItemAccountResponse;
import com.example.doancuoiky.Home.fragment_home;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_create_transfer extends Fragment {
    SharedPreferences sharedPreferences_2;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private String KEY_USERNAME;
    private EditText et_transfer_money;
    private Spinner from_bank, to_bank;
    private TextView Description_Transfer, tv_notify_create_transfer;
    private Button btn_continue_create_transfer;
    private ArrayList<ItemAccountResponse> array;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back_transfer);
        et_transfer_money = (EditText) view.findViewById(R.id.et_transfer_money);
        Description_Transfer = (TextView) view.findViewById(R.id.Description_Transfer);
        tv_notify_create_transfer = (TextView) view.findViewById(R.id.tv_notify_create_transfer);
        btn_continue_create_transfer = (Button) view.findViewById(R.id.btn_continue_create_transfer);
        from_bank = (Spinner) view.findViewById(R.id.Wallet_Transfer1);
        to_bank = (Spinner) view.findViewById(R.id.Wallet_Transfer2);

        array = new ArrayList<ItemAccountResponse>();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        getNameBank(view);

        btn_continue_create_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(et_transfer_money.getText().toString()) == 0) {
                    return;
                }
                createTransfer(view);
            }
        });

        return view;
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

                    ArrayAdapter<ItemAccountResponse> spinFromBankAdapter = new ArrayAdapter<ItemAccountResponse>(getActivity(), android.R.layout.simple_spinner_item, array);
                    spinFromBankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    from_bank.setAdapter(spinFromBankAdapter);
                    from_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    ArrayAdapter<ItemAccountResponse> spinToBankAdapter = new ArrayAdapter<ItemAccountResponse>(getActivity(), android.R.layout.simple_spinner_item, array);
                    spinToBankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    to_bank.setAdapter(spinToBankAdapter);
                    to_bank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    public void createTransfer(View view) {
        if (from_bank.getSelectedItem().toString().equals(to_bank.getSelectedItem().toString())) {
            tv_notify_create_transfer.setText("Can not choose the same account!");
            return;
        }

        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences_2.getString(KEY_USERNAME, null);

        String sTextFromET = et_transfer_money.getText().toString();
        int amount = new Integer(sTextFromET).intValue();

        TransferRequest transferRequest = new TransferRequest(
                value,
                from_bank.getSelectedItem().toString(),
                to_bank.getSelectedItem().toString(),
                amount,
                Description_Transfer.getText().toString());

        Call<TransferResponse> create_transferCall = ApiClient.getUserService().create_transferCall(transferRequest);
        create_transferCall.enqueue(new Callback<TransferResponse>() {
            @Override
            public void onResponse(Call<TransferResponse> call, Response<TransferResponse> response) {
                if (response.code() == 250) {
                    tv_notify_create_transfer.setText(response.body().getMessage());
                    return;
                }

                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.fade_in,  // enter
                                R.anim.fade_out // exit
                        )
                        .replace(R.id.container, new fragment_home())
                        .commit();
            }

            @Override
            public void onFailure(Call<TransferResponse> call, Throwable t) {
            }
        });
}
}



