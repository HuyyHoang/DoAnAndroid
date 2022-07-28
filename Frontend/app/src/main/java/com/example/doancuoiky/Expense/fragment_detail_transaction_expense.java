package com.example.doancuoiky.Expense;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.Screen.MainScreen;
import com.example.doancuoiky.R;
import com.example.doancuoiky.transaction.ItemResponse;


public class fragment_detail_transaction_expense extends AppCompatActivity {
    TextView detailAmount_2, categoryNamee_2, detailHour_2, detailType_2, detailWallet_2, detailDes_2, detailTime_2;
    Button btnContinue_2;
    private ImageButton btnBack, btnRemove;

    SharedPreferences sharedPreferences_3;
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_ACCOUNT, account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_transaction_expense);

        detailAmount_2 = findViewById(R.id.detail_amount_2);
        categoryNamee_2 = findViewById(R.id.category_name_2);
        detailType_2 = findViewById(R.id.detail_type_2);
        detailWallet_2 = findViewById(R.id.detail_wallet_2);
        detailDes_2 = findViewById(R.id.detail_des_2);
        detailHour_2 = findViewById(R.id.detail_hour_2);
        detailTime_2 = findViewById(R.id.detail_time_2);

        btnBack = findViewById(R.id.btn_back_detail_budget_2);
        btnRemove = findViewById(R.id.remove_budget_2);

        sharedPreferences_3 = getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
        account = sharedPreferences_3.getString(KEY_ACCOUNT, null);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            ItemResponse itemResponse = (ItemResponse) intent.getSerializableExtra("data");
            String amount = itemResponse.getAmount();
            String categoryName = itemResponse.getCategoryName();
            String name = itemResponse.getName();
            String wallet = account;
            String description = itemResponse.getDescription();
            String hour = itemResponse.getHour();
            String dayMonth = itemResponse.getDaymonth();

            categoryNamee_2.setText(categoryName);
            detailAmount_2.setText(amount);
            detailWallet_2.setText(wallet);
            detailDes_2.setText(description);
            detailType_2.setText(name);
            detailHour_2.setText(hour);
            detailTime_2.setText(dayMonth);

        }


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_detail_transaction_expense.this, MainScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_the_right);
            }
        });


    }



}