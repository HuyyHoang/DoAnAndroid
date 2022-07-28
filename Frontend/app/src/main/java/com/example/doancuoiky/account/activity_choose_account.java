package com.example.doancuoiky.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.Screen.MainScreen;
import com.example.doancuoiky.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class activity_choose_account extends AppCompatActivity {
    private ListView lv_choose_account;

    SharedPreferences sharedPreferences_2, sharedPreferences_3;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_USERNAME, KEY_ACCOUNT, value;
    private ArrayList<ItemAccountResponse> array;
    private ItemAccountAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_account);


        lv_choose_account = (ListView) findViewById(R.id.lv_choose_account);
        sharedPreferences_2 = getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        sharedPreferences_3 = getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);

        value = sharedPreferences_2.getString(KEY_USERNAME, null);

        array = new ArrayList<ItemAccountResponse>();
        getData();

        lv_choose_account.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor3 = sharedPreferences_3.edit();
                editor3.putString(KEY_ACCOUNT, array.get(position).toString());
                editor3.apply();

                startActivity(new Intent(activity_choose_account.this, MainScreen.class));
            }
        });
    }

    public void getData() {
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
                    adapter = new ItemAccountAdapter(activity_choose_account.this,1, array);
                    lv_choose_account.setAdapter(adapter);

                }
            }

            @Override
            public void onFailure(Call<List<ItemAccountResponse>> call, Throwable t) {
            }
        });
    }
}
