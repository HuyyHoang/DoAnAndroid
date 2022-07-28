package com.example.doancuoiky.transaction;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.API.ApiClient;
import com.example.doancuoiky.R;
import com.example.doancuoiky.Report.activity_report;
import com.example.doancuoiky.Expense.fragment_detail_transaction_expense;
import com.example.doancuoiky.Income.fragment_detail_transaction_income;
import com.example.doancuoiky.transaction.Filter.ChooseCategory;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_transaction extends Fragment implements AdapterView.OnItemSelectedListener, ItemAdapter.ClickedItem {
    public static final String TAG = fragment_transaction.class.getName();

    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    ImageButton filter_button;

    Spinner mySpinner;

    SharedPreferences sharedPreferences_2, sharedPreferences_3;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_USERNAME, KEY_ACCOUNT, user_name, account_name, filterby, sortby;

    private Chip chipIncome, chipExpense, chipTransfer;
    private Chip chipNewest, chipHighest, chipLowest, chipOldest;
    private ChipGroup chipGroup1, chipGroup2;

    private Button btnReset;

    private LinearLayout linear_choose_category;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction, container, false);

        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2,MODE_PRIVATE);
        user_name = sharedPreferences_2.getString(KEY_USERNAME, null);

        sharedPreferences_3 = getActivity().getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
        account_name = sharedPreferences_3.getString(KEY_ACCOUNT, null);

        TextView textView = (TextView) view.findViewById(R.id.textview11);

        Context context;
        Resources resources;
        context = LocaleHelper.setLocale(getActivity(), LocaleHelper.getLanguage(getActivity()));
        resources = context.getResources();

        textView.setText(resources.getString(R.string.textview11));

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_transaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));

        itemAdapter = new ItemAdapter(this::ClickedTransactionItem);

        getData();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ArrayList<String> arr = bundle.getStringArrayList("key_category");
            Log.d("arraylist", arr.toString());
        }

        filter_button = (ImageButton) view.findViewById(R.id.filter_button);
        filter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                        getActivity(), R.style.BottomSheetDialogTheme
                );
                View bottomSheetView = LayoutInflater.from(getContext())
                        .inflate(
                                R.layout.fragment_filter,
                                (LinearLayout) view.findViewById(R.id.bottom_sheet_container)
                        );

                chipIncome = (Chip) bottomSheetView.findViewById(R.id.chip_income);
                chipExpense = (Chip) bottomSheetView.findViewById(R.id.chip_expense);

                chipHighest = (Chip) bottomSheetView.findViewById(R.id.chip_highest);
                chipLowest = (Chip) bottomSheetView.findViewById(R.id.chip_lowest);
                chipNewest = (Chip) bottomSheetView.findViewById(R.id.chip_newest);
                chipOldest = (Chip) bottomSheetView.findViewById(R.id.chip_oldest);

                chipGroup1 = (ChipGroup) bottomSheetView.findViewById(R.id.chipGroup_1);
                chipGroup2 = (ChipGroup) bottomSheetView.findViewById(R.id.chipGroup_2);

                linear_choose_category = (LinearLayout) bottomSheetView.findViewById(R.id.choose_category);

                btnReset = (Button) bottomSheetView.findViewById(R.id.btn_reset_filter);
                btnReset.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        resetChip();
                    }
                });

                linear_choose_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(getActivity(), ChooseCategory.class));
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .addToBackStack(ChooseCategory.class.getName())
                                .replace(R.id.container, new ChooseCategory())
                                .commit();
                        bottomSheetDialog.dismiss();
                    }
                });

                bottomSheetView.findViewById(R.id.btn_apply).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getAllItem(filterby, sortby);
                        filterby = "";
                        bottomSheetDialog.dismiss();

                    }
                });

                chipExpense.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filterby = chipExpense.getText().toString();
                    }
                });

                chipIncome.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        filterby = chipIncome.getText().toString();
                    }
                });

                chipHighest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sortby = chipHighest.getText().toString();
                    }
                });

                chipLowest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sortby = chipLowest.getText().toString();
                    }
                });

                chipNewest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sortby = chipNewest.getText().toString();
                    }
                });

                chipOldest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sortby = chipOldest.getText().toString();
                    }
                });

                bottomSheetDialog.setContentView(bottomSheetView);
                bottomSheetDialog.show();

            }
        });

        Spinner mySpinner = (Spinner) view.findViewById(R.id.month_spinner);
        ArrayAdapter<CharSequence> myAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.month, android.R.layout.simple_spinner_item);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);
        mySpinner.setOnItemSelectedListener(this);


        LinearLayout seeFinancial = (LinearLayout) view.findViewById(R.id.financial_report_button);
        seeFinancial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(), activity_report.class);
                startActivity(intent);

            }
        });

        return view;
    }

    // <------------------------------------ Chip Chip ------------------------------------->
    public void resetChip(){
        chipGroup1.clearCheck();
        chipGroup2.clearCheck();
    }
    // <------------------------------------ Transaction ------------------------------------->

    private void getAllItem(String filter, String sortby){
        Call<List<ItemResponse>> itemList = ApiClient.getUserService().getAllTracsaction(user_name, account_name, filter, sortby);
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

    public void getData() {
        ItemRequest itemRequest = new ItemRequest(user_name,account_name);

        Call<List<ItemResponse>> itemList = ApiClient.getUserService().getAllItem(itemRequest);
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
    // <-------------------------------------------------------------------------------------->

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}