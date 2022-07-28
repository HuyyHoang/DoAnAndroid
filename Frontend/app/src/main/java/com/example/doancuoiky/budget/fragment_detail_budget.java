package com.example.doancuoiky.budget;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.Screen.MainScreen;
import com.example.doancuoiky.R;


public class fragment_detail_budget extends AppCompatActivity {
    private ImageButton btn_back;
    TextView detailBudgetName, detailBudgetRemain;
    ImageView detailBudgetWarning, detailBudgetIcon;
    Button btn_edit_detail_budget;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_budget);

        detailBudgetIcon = findViewById(R.id.budget_icon);
        detailBudgetName = findViewById(R.id.detail_budget_name);
        detailBudgetRemain = findViewById(R.id.detail_budget_remain);
        detailBudgetWarning = findViewById(R.id.detail_budget_warning);

        btn_back = findViewById(R.id.btn_back_detail_budget);
        ImageButton remove = findViewById(R.id.remove_budget);


        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(fragment_detail_budget.this, MainScreen.class));
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDiaLog();
            }
        });


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            BudgetResponse budgetResponse = (BudgetResponse) intent.getSerializableExtra("data");
            String name = budgetResponse.getBudgetName();
            String remain = budgetResponse.getAmount_Remain();

            detailBudgetName.setText(name);
            detailBudgetRemain.setText("$" +remain);
            if (detailBudgetName.equals("Housing")){
                detailBudgetIcon.setImageResource(R.drawable.ic_home);
            }
             else if (name.equals("Food & Drink")){
                detailBudgetIcon.setImageResource(R.drawable.ic_food);
            }
            else if (name.equals("Education")){
                detailBudgetIcon.setImageResource(R.drawable.ic_education);
            }
            else if (name.equals("Transportation")){
                detailBudgetIcon.setImageResource(R.drawable.ic_car);
            }
            else if (name.equals("Utilities")){
                detailBudgetIcon.setImageResource(R.drawable.ic_computer);
            }
            else if (name.equals("Insurance")){
                detailBudgetIcon.setImageResource(R.drawable.ic_pill);
            }
            else if (name.equals("Medical & Healthcare")){
                detailBudgetIcon.setImageResource(R.drawable.ic_medical);
            }
            else if (name.equals("Debt")){
                detailBudgetIcon.setImageResource(R.drawable.icon_debt);
            }
            else if (name.equals("Clothing")){
                detailBudgetIcon.setImageResource(R.drawable.ic_clothes);
            }
            else if (name.equals("Personal")){
                detailBudgetIcon.setImageResource(R.drawable.ic_people);
            }


        }

    }

    public void showAlertDiaLog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(fragment_detail_budget.this);
        alertDialog.setTitle("Remove this Budget");
        alertDialog.setMessage("Are you sure do you wanna remove this budget ?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertDialog.show();
    }
}



