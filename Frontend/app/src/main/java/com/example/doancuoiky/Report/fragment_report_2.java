package com.example.doancuoiky.Report;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.Fragment;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.R;
import com.example.doancuoiky.transaction.ChartReponse;
import com.example.doancuoiky.transaction.ChartRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_report_2 extends Fragment implements AdapterView.OnItemSelectedListener {
    private ArrayList<ChartReponse> array;
    ArrayList yVals = new ArrayList();

    public  ArrayList<ChartReponse> categoryName;
    public  ArrayList<ChartReponse>  amount;
    private  ArrayList<ChartReponse>  name;

    private PieChart pieChart;

    SharedPreferences sharedPreferences2, sharedPreferences_3;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_USERNAME, KEY_ACCOUNT;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report_2, container, false);

        categoryName = new ArrayList<>();
        name = new ArrayList<ChartReponse>();
        amount = new ArrayList<>();

        categoryName = new ArrayList<ChartReponse>();
        name = new ArrayList<ChartReponse>();
        amount = new ArrayList<ChartReponse>();


        pieChart = (PieChart) view.findViewById(R.id.pieChart_income);

        getChartResponseArray();
        pieChart.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void getChartResponseArray() {
        sharedPreferences2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences2.getString(KEY_USERNAME, null);

        sharedPreferences_3 = getActivity().getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
        String account_name = sharedPreferences_3.getString(KEY_ACCOUNT, null);

        ChartRequest chartRequest = new ChartRequest(value, account_name);
        Call<List<ChartReponse>> getChartResponse = ApiClient.getUserService().getChartResponse(chartRequest);
        getChartResponse.enqueue(new Callback<List<ChartReponse>>() {
            @Override
            public void onResponse(Call<List<ChartReponse>> call, Response<List<ChartReponse>> response) {

                try {
                    List<ChartReponse> items = response.body();
                    yVals = new ArrayList<>();
                    for (int i = 0; i < items.size(); i++) {
                        String x = items.get(i).getAmount();
                        String y = items.get(i).getCategoryName();
                        int z = Integer.parseInt(x);
                        yVals.add(new PieEntry(z, y));

                        PieDataSet dataSet = new PieDataSet(yVals, "");
                        dataSet.setValueTextSize(15f);
                        dataSet.notifyDataSetChanged();
                        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                        PieData data = new PieData(dataSet);
                        pieChart.animateXY(2000, 2000);
                        pieChart.getDescription().setText("Your Income This Month");
                        pieChart.getDescription().setTextSize(10f);
                        pieChart.notifyDataSetChanged();
                        pieChart.setData(data);
                        pieChart.invalidate();
                    }
                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<List<ChartReponse>> call, Throwable t) {

            }


        });
    }
}