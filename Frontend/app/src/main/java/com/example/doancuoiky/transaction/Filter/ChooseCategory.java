package com.example.doancuoiky.transaction.Filter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;
import com.example.doancuoiky.transaction.fragment_transaction;

import java.util.ArrayList;

public class ChooseCategory extends Fragment implements  CategoryListener, View.OnClickListener{
    RecyclerView recyclerView_category;
    CategoryAdapter categoryAdapter;
    Button btn_apply_category;
    ImageButton btn_back_choose_category;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_choose_category, container, false);

        recyclerView_category = (RecyclerView) view.findViewById(R.id.recyclerview_choose_category);
        btn_apply_category = (Button) view.findViewById(R.id.btn_apply_category);
        btn_back_choose_category = (ImageButton) view.findViewById(R.id.btn_back_choose_category);

        recyclerView_category.setHasFixedSize(true);
        recyclerView_category.setLayoutManager(new LinearLayoutManager(getActivity()));

        categoryAdapter = new CategoryAdapter(getActivity(), getCategory(), this);
        recyclerView_category.setAdapter(categoryAdapter);

        btn_apply_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrayList_0 = categoryAdapter.getArrayList_0();
                if(arrayList_0.size() != 0) {
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("key_category", arrayList_0);

                    fragment_transaction frag = new fragment_transaction();
                    frag.setArguments(bundle);
                    getActivity()
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, frag)
                            .commit();
                }
            }
        });

        btn_back_choose_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity()
                        .getSupportFragmentManager()
                        .popBackStack();
            }
        });



        return view;
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        recyclerView_category = (RecyclerView) findViewById(R.id.recyclerview_choose_category);
        btn_apply_category = (Button) findViewById(R.id.btn_apply_category);
        btn_back_choose_category = (ImageButton) findViewById(R.id.btn_back_choose_category);

        recyclerView_category.setHasFixedSize(true);
        recyclerView_category.setLayoutManager(new LinearLayoutManager(this));

        categoryAdapter = new CategoryAdapter(this, getCategory(), this);
        recyclerView_category.setAdapter(categoryAdapter);

        btn_apply_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> arrayList_0 = categoryAdapter.getArrayList_0();
                if(arrayList_0.size() != 0) {

                }
            }
        });

        btn_back_choose_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

     */



    private ArrayList<String> getCategory() {
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Salary");
        arrayList.add("Gift & Donation");
        arrayList.add("Loan");
        arrayList.add("Scholarship");
        arrayList.add("Investment");
        arrayList.add("Lottery");

        arrayList.add("Housing");
        arrayList.add("Food & Drink");
        arrayList.add("Education");
        arrayList.add("Transportation");
        arrayList.add("Utilities");
        arrayList.add("Insurance");
        arrayList.add("Medical & Healthcare");
        arrayList.add("Debt");
        arrayList.add("Clothing");
        arrayList.add("Personal");
        return arrayList;
    }

    @Override
    public void onCategoryChange(ArrayList<String> arrayList) {

    }

    @Override
    public void onClick(View v) {

    }
}
