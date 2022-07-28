package com.example.doancuoiky.budget;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_create_budget extends Fragment implements AdapterView.OnItemSelectedListener {

    EditText budgetAmount;
    Spinner budgetCategorySpinner;
    Button btnContinue;
    private ArrayList<GetCategoryResponse> array;

    SharedPreferences sharedPreferences_2, sharedPreferences_3;
    private final static String SHARED_PREF_NAME_2 = "MYPREF_2";
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_USERNAME, KEY_ACCOUNT, value, account_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_budget, container, false);
        ImageButton btn_back = (ImageButton) view.findViewById(R.id.btn_back_create_budget);
        Button btn_continue = (Button) view.findViewById(R.id.btn_continue_create_budget);
        budgetAmount = (EditText) view.findViewById(R.id.edit_text_create_budget_amount);
        budgetCategorySpinner = (Spinner) view.findViewById(R.id.Category_budget);

        array = new ArrayList<GetCategoryResponse>();


        sharedPreferences_2 = getActivity().getSharedPreferences(SHARED_PREF_NAME_2, MODE_PRIVATE);
        value = sharedPreferences_2.getString(KEY_USERNAME, null);

        sharedPreferences_3 = getActivity().getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
        account_name = sharedPreferences_3.getString(KEY_ACCOUNT, null);


        getCategory(view);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(
                                R.anim.slide_in_from_left,  // enter
                                R.anim.slide_out_to_the_right // exit
                        )
                        .replace(R.id.container,new fragment_Budget())
                        .commit();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBudget(createBudgetRequest());
            }
        });
        return view;
    }

    public CreateBudgetRequest createBudgetRequest(){


        String sTextFromET = budgetAmount.getText().toString();
        int nIntFromET = new Integer(sTextFromET).intValue();

        CreateBudgetRequest createBudgetRequest = new CreateBudgetRequest();
        createBudgetRequest.setUsername(value);
        createBudgetRequest.setAccountName(account_name);
        createBudgetRequest.setBudgetAmount(nIntFromET);
        createBudgetRequest.setBudgetName(budgetCategorySpinner.getSelectedItem().toString());
        return createBudgetRequest;
    }

    public void getCategory(View view) {
        GetCategoryRequest getCategoryRequest = new GetCategoryRequest(value,account_name);
        Call<List<GetCategoryResponse>> getCategoryResponseCall = ApiClient.getUserService().getlistNotExistCategory(getCategoryRequest);
        getCategoryResponseCall.enqueue(new Callback<List<GetCategoryResponse>>() {
            @Override
            public void onResponse(Call<List<GetCategoryResponse>> call, Response<List<GetCategoryResponse>> response) {
                if (response.isSuccessful()) {
                    List<GetCategoryResponse> items = response.body();
                    for (GetCategoryResponse item: items) {
                        array.add(new GetCategoryResponse(item.getName()));
                    }

                    ArrayAdapter<GetCategoryResponse> spinAccountAdapter = new ArrayAdapter<GetCategoryResponse>(getActivity(), android.R.layout.simple_spinner_item, array);
                    spinAccountAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    budgetCategorySpinner.setAdapter(spinAccountAdapter);
                    budgetCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            public void onFailure(Call<List<GetCategoryResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "Throwable "+t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createBudget (CreateBudgetRequest createBudgetRequest){
        Call<CreateBudgetResponse> create = ApiClient.getUserService().createBudget(createBudgetRequest);
        create.enqueue(new Callback<CreateBudgetResponse>() {
            @Override
            public void onResponse(Call<CreateBudgetResponse> call, Response<CreateBudgetResponse> response) {
                if (response.isSuccessful()) {
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .setCustomAnimations(
                                    R.anim.slide_in_from_right,  // enter
                                    R.anim.slide_out_to_the_left // exit
                            )
                            .replace(R.id.container,new fragment_Budget())
                            .commit();
                }
            }

            @Override
            public void onFailure(Call<CreateBudgetResponse> call, Throwable t) {


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}