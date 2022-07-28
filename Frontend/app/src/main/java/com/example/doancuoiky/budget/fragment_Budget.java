package com.example.doancuoiky.budget;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_Budget extends Fragment implements BudgetAdapter.ClickedBudget{
    public static final String TAG = fragment_Budget.class.getName();

    ImageButton previous,nextmonth;
    TextView calenderview;
    private RecyclerView recyclerView;
    BudgetAdapter budgetAdapter;
    private int year, month, day;
    private ProgressBar progressBar;
    private Button btnCreate;

    SharedPreferences sharedPreferences2, sharedPreferences_3;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_USERNAME, KEY_ACCOUNT;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);


        previous = (ImageButton) view.findViewById(R.id.btn_previous_month);
        nextmonth = (ImageButton) view.findViewById(R.id.btn_next_month);
        calenderview = (TextView) view.findViewById(R.id.textview_month_budget);

        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();

        previous = (ImageButton) view.findViewById(R.id.btn_previous_month);
        nextmonth = (ImageButton) view.findViewById(R.id.btn_next_month);
        calenderview = (TextView) view.findViewById(R.id.textview_month_budget);

        month = Integer.parseInt(dateFormat.format(date));
        calenderview.setText(String.valueOf(month));

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_budget);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        btnCreate = (Button) view.findViewById(R.id.btn_create_budget);

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calenderview.setText(getpreviousmonth());
            }
        });
        nextmonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calenderview.setText(getnextmonth());
            }
        });

        budgetAdapter = new BudgetAdapter(this::ClickedBudgetItem);
        getAllBudget();


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.fade_in,  // enter
                                R.anim.fade_out // exit
                        )
                        .replace(R.id.container, new fragment_create_budget())
                        .commit();
            }
        });


        return view;
    }

    private String getnextmonth() {
        if (month == 12) {
            return String.valueOf(month);
        }
        month += 1;
        getAllBudget();
        return  String.valueOf(month);
    }

    private String getpreviousmonth() {
        if (month == 1) {
            return String.valueOf(month);
        }
        month -= 1;
        getAllBudget();
        return  String.valueOf(month);
    }


    private void getAllBudget(){
        sharedPreferences2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        String value = sharedPreferences2.getString(KEY_USERNAME, null);

        sharedPreferences_3 = getActivity().getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
        String account_name = sharedPreferences_3.getString(KEY_ACCOUNT, null);

        BudgetRequest budgetRequest = new BudgetRequest(value, account_name, month);
        Call<List<BudgetResponse>> getBudgetResponse = ApiClient.getUserService().getBudgetResponse(budgetRequest);
        getBudgetResponse.enqueue(new Callback<List<BudgetResponse>>() {
            @Override
            public void onResponse(Call<List<BudgetResponse>> call, Response<List<BudgetResponse>> response) {
                if (response.isSuccessful()) {
                    List<BudgetResponse> items = response.body();
                    budgetAdapter.setData(items);
                    recyclerView.setAdapter(budgetAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<BudgetResponse>> call, Throwable t) {

            }
        });
    }

    @Override
    public void ClickedBudgetItem(BudgetResponse budgetResponse) {
        startActivity(new Intent(getActivity(), fragment_detail_budget.class).putExtra("data",budgetResponse));

    }
}