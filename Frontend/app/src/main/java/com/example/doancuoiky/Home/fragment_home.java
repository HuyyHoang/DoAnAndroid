package com.example.doancuoiky.Home;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.Expense.fragment_detail_transaction_expense;
import com.example.doancuoiky.Income.fragment_detail_transaction_income;
import com.example.doancuoiky.R;
import com.example.doancuoiky.account.GetBalanceRequest;
import com.example.doancuoiky.account.GetBalanceResponse;
import com.example.doancuoiky.account.GetIncomeExpenseRequest;
import com.example.doancuoiky.account.GetIncomeExpenseResponse;
import com.example.doancuoiky.transaction.ItemAdapter;
import com.example.doancuoiky.transaction.ItemRequest;
import com.example.doancuoiky.transaction.ItemResponse;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import com.example.doancuoiky.transaction.LocaleHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class fragment_home extends Fragment implements ItemAdapter.ClickedItem {
    public static final String TAG = fragment_home.class.getName();
    private TextView balance, tv_home_income, tv_home_expense, tv_month;
    SharedPreferences sharedPreferences_2, sharedPreferences_3;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_USERNAME, KEY_ACCOUNT, user_name, account_name;
    private Integer money;
    private LineChart mChart;

    ArrayList line = new ArrayList();

    RecyclerView recyclerView;
    ItemAdapter itemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        sharedPreferences_3 = getActivity().getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
        balance = (TextView) view.findViewById(R.id.tv_account_money);
        tv_home_expense = (TextView) view.findViewById(R.id.tv_home_expense);
        tv_home_income = (TextView) view.findViewById(R.id.tv_home_income);


        TextView textView7 = (TextView) view.findViewById(R.id.textview7);
        TextView income= (TextView) view.findViewById(R.id.textview_income_home);
        TextView expense= (TextView) view.findViewById(R.id.textview_expense_home);
        TextView textView8 = (TextView) view.findViewById(R.id.textview8);
        TextView textView9 = (TextView) view.findViewById(R.id.textview9);

        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(getActivity(), LocaleHelper.getLanguage(getActivity()));
        resources = context.getResources();

        textView7.setText(resources.getString(R.string.textview7));
        income.setText(resources.getString(R.string.textview_income_home));
        expense.setText(resources.getString(R.string.textview_expense_home));
        textView8.setText(resources.getString(R.string.textview8));
        textView9.setText(resources.getString(R.string.textview9));
        tv_month = (TextView) view.findViewById(R.id.tv_month);
/*
        btnreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), activity_story.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_the_left);
            }
        });

 */
        DateFormat dateFormat = new SimpleDateFormat("MMMM");
        Date date = new Date();
        tv_month.setText(dateFormat.format(date));

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_transaction_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        itemAdapter = new ItemAdapter(this::ClickedTransactionItem);


        mChart = (LineChart) view.findViewById(R.id.lineChart);


        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(false);



        getChartResponseArray();

        getBalance();
        getIncome();
        getExpense();
        getTransaction();
        return view;
    }

    public void getBalance() {
        user_name = sharedPreferences_2.getString(KEY_USERNAME, null);
        account_name = sharedPreferences_3.getString(KEY_ACCOUNT, null);

        GetBalanceRequest get_req = new GetBalanceRequest(user_name, account_name);
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

    public void getIncome() {
        GetIncomeExpenseRequest getIncomeExpenseRequest = new GetIncomeExpenseRequest(user_name, account_name);
        Call<GetIncomeExpenseResponse> getIncomeCall = ApiClient.getUserService().getIncomeCall(getIncomeExpenseRequest);
        getIncomeCall.enqueue(new Callback<GetIncomeExpenseResponse>() {
            @Override
            public void onResponse(Call<GetIncomeExpenseResponse> call, Response<GetIncomeExpenseResponse> response) {
                if (response.isSuccessful()) {
                    GetIncomeExpenseResponse res = response.body();
                    tv_home_income.setText(String.valueOf(res.getTotal()));
                }
            }

            @Override
            public void onFailure(Call<GetIncomeExpenseResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getExpense() {
        GetIncomeExpenseRequest getIncomeExpenseRequest = new GetIncomeExpenseRequest(user_name, account_name);
        Call<GetIncomeExpenseResponse> getIncomeCall = ApiClient.getUserService().getExpenseCall(getIncomeExpenseRequest);
        getIncomeCall.enqueue(new Callback<GetIncomeExpenseResponse>() {
            @Override
            public void onResponse(Call<GetIncomeExpenseResponse> call, Response<GetIncomeExpenseResponse> response) {
                if (response.isSuccessful()) {
                    GetIncomeExpenseResponse getBalanceResponse = response.body();
                    tv_home_expense.setText(String.valueOf(getBalanceResponse.getTotal()));
                }
            }

            @Override
            public void onFailure(Call<GetIncomeExpenseResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void getTransaction() {
        ItemRequest itemRequest = new ItemRequest(user_name,account_name);

        Call<List<ItemResponse>> itemList = ApiClient.getUserService().get_3Item(itemRequest);
        itemList.enqueue(new Callback<List<ItemResponse>>() {
            @Override
            public void onResponse(Call<List<ItemResponse>> call, Response<List<ItemResponse>> response) {
                if (response.isSuccessful()){
                    List<ItemResponse> itemResponses = response.body();
                    itemAdapter.setData(itemResponses);
                    recyclerView.setAdapter(itemAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<ItemResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    public void ClickedTransactionItem(ItemResponse itemResponse) {
        if (itemResponse.getName().equals("Income")){
            startActivity(new Intent(getActivity(), fragment_detail_transaction_income.class).putExtra("data",itemResponse));
        }
        else {
            startActivity(new Intent(getActivity(), fragment_detail_transaction_expense.class).putExtra("data", itemResponse));
        }

    }

    private void getChartResponseArray() {
        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences_2.getString(KEY_USERNAME, null);

        sharedPreferences_3 = getActivity().getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
        String account_name = sharedPreferences_3.getString(KEY_ACCOUNT, null);

        SpendRequest spendRequest = new SpendRequest(value, account_name);
        Call<List<SpendResponse>> getSpendResponse = ApiClient.getUserService().getSpendResponse(spendRequest);
        getSpendResponse.enqueue(new Callback<List<SpendResponse>>() {
            @Override
            public void onResponse(Call<List<SpendResponse>> call, Response<List<SpendResponse>> response) {
                try {
                    List<SpendResponse> items = response.body();
                    line = new ArrayList<>();
                    for (int i = 0; i < items.size(); i++) {
                        String x = items.get(i).getFrequency();
                        String y = items.get(i).getDay();
                        String[] parts = y.split("-");
                        String date =  parts[2];
                        Float a = Float.parseFloat(date);
                        int z = Integer.parseInt(x);
                        line.add(new Entry(a, z));
                    }
                    LineDataSet set;
                    set = new LineDataSet(line, "");
                    set.setColors(Color.BLUE);
                    set.setDrawCircles(true);
                    set.setLineWidth(3f);
                    set.setFillAlpha(255);
                    set.setDrawFilled(true);
                    YAxis rightYAxis = mChart.getAxisRight();
                    rightYAxis.setEnabled(false);


                    XAxis xAxis = mChart.getXAxis();
                    xAxis.setGranularity(1f);
                    xAxis.setCenterAxisLabels(true);
                    xAxis.setEnabled(true);
                    xAxis.setDrawGridLines(false);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    set.notifyDataSetChanged();


                    LineData data = new LineData(set);
                    data.setDrawValues(true);
                    mChart.getLegend().setEnabled(false);
                    mChart.getDescription().setEnabled(false);
                    mChart.notifyDataSetChanged();
                    mChart.setData(data);
                    mChart.invalidate();





                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<SpendResponse>> call, Throwable t) {

            }


        });
    }


}