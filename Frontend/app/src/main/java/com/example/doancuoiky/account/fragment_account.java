package com.example.doancuoiky.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.Profile.fragment_Profile;
import com.example.doancuoiky.R;
import com.example.doancuoiky.Screen.MainScreen;
import com.example.doancuoiky.transaction.LocaleHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_account extends Fragment {

    private ListView lv;
    private ArrayList<ItemAccountResponse> array;
    private ItemAccountAdapter adapter;
    private TextView balance;

    SharedPreferences sharedPreferences2, sharedPreferences_3;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_USERNAME, KEY_ACCOUNT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_account, container, false);


        TextView title = (TextView) view.findViewById(R.id.title_account);
        TextView textView6 = (TextView) view.findViewById(R.id.textview6);
        TextView btn_continue = (TextView) view.findViewById(R.id.btn_add_account);

        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(getActivity(), LocaleHelper.getLanguage(getActivity()));
        resources = context.getResources();

        title.setText(resources.getString(R.string.title_account));
        textView6.setText(resources.getString(R.string.textview6));
        btn_continue.setText(resources.getString(R.string.btn_add_account));

        ImageButton btn_back_account = (ImageButton) view.findViewById(R.id.btn_back_account);
        Button btn_add_account = (Button) view.findViewById(R.id.btn_add_account);

        balance = (TextView) view.findViewById(R.id.tv_account_money_2);

        btn_back_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_from_left,  // enter
                                R.anim.slide_out_to_the_right // exit
                        )
                        .replace(R.id.container,new fragment_Profile())
                        .commit();
            }
        });

        btn_add_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_from_right,  // enter
                                R.anim.slide_out_to_the_left // exit
                        )
                        .replace(R.id.container,new fragment_create_account())
                        .commit();
            }
        });

        array = new ArrayList<ItemAccountResponse>();
        lv = (ListView) view.findViewById(R.id.lv_account);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor3 = sharedPreferences_3.edit();
                editor3.putString(KEY_ACCOUNT, array.get(position).toString());
                editor3.apply();

                startActivity(new Intent(getActivity(), MainScreen.class));
            }
        });
        getData();
        getBalance();
        return view;
    }

    public void getData() {
        sharedPreferences2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences2.getString(KEY_USERNAME, null);

        ItemAccountRequest request = new ItemAccountRequest(value);

        Call<List<ItemAccountResponse>> getList = ApiClient.getUserService().getlistAccount(request);
        getList.enqueue(new Callback<List<ItemAccountResponse>>() {
            @Override
            public void onResponse(Call<List<ItemAccountResponse>> call, Response<List<ItemAccountResponse>> response) {
                if (response.isSuccessful()) {
                    List<ItemAccountResponse> items = response.body();
                    for (ItemAccountResponse item: items) {
                        array.add(new ItemAccountResponse(item.getName(), item.getAmount()));
                    }
                    adapter = new ItemAccountAdapter(getActivity(),1, array);
                    lv.setAdapter(adapter);

                }
                else {
                    Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<ItemAccountResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getBalance() {
        sharedPreferences2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences2.getString(KEY_USERNAME, null);

        sharedPreferences_3 = getActivity().getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
        String name = sharedPreferences_3.getString(KEY_ACCOUNT, null);

        GetBalanceRequest get_req = new GetBalanceRequest(value, name);
        Call<GetBalanceResponse> create_accountCall = ApiClient.getUserService().getBalance(get_req);
        create_accountCall.enqueue(new Callback<GetBalanceResponse>() {
            @Override
            public void onResponse(Call<GetBalanceResponse> call, Response<GetBalanceResponse> response) {
                if (response.isSuccessful()) {
                    GetBalanceResponse res = response.body();
                    balance.setText(String.valueOf(res.getAmount()));
                }
            }

            @Override
            public void onFailure(Call<GetBalanceResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

}