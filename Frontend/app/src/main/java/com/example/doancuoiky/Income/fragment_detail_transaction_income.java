package com.example.doancuoiky.Income;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doancuoiky.Screen.MainScreen;
import com.example.doancuoiky.R;
import com.example.doancuoiky.transaction.ItemResponse;

public class fragment_detail_transaction_income extends AppCompatActivity {

    TextView detailAmount, categoryNamee, detailHour, detailType, detailWallet, detailDes, detailTime;
    ImageView attachmentImage;
    Button btnContinue;
    private ImageButton btnBack, btnRemove;

    SharedPreferences sharedPreferences_3;
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_ACCOUNT, account;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detail_transaction_income);

        detailAmount = findViewById(R.id.detail_amount);
        categoryNamee = findViewById(R.id.category_name);
        detailType = findViewById(R.id.detail_type);
        detailWallet = findViewById(R.id.detail_wallet);
        detailDes = findViewById(R.id.detail_des);
        detailHour = findViewById(R.id.detail_hour);
        detailTime = findViewById(R.id.detail_time);

        btnBack = findViewById(R.id.btn_back_detail_budget);
        btnRemove = findViewById(R.id.remove_budget);

        attachmentImage = findViewById(R.id.attachment_image);
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

            categoryNamee.setText(categoryName);
            detailAmount.setText(amount);
            detailWallet.setText(wallet);
            detailDes.setText(description);
            detailType.setText(name);
            detailHour.setText(hour);
            detailTime.setText(dayMonth);


        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_detail_transaction_income.this, MainScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_the_right);
            }
        });

    }

}