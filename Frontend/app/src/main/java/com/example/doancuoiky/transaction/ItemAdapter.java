package com.example.doancuoiky.transaction;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemAdapterVH> {

    private List<ItemResponse> itemResponseList;
    private Context context;
    private ClickedItem clickedItem;

    SharedPreferences sharedPreferences_3;
    private final static String SHARED_PREF_NAME_3 = "ACCOUNT";
    private String KEY_ACCOUNT, account;

    public ItemAdapter(ClickedItem clickedItem) {
        this.clickedItem = clickedItem;
    }


    public void setData(List<ItemResponse> itemResponseList) {
        this.itemResponseList = itemResponseList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ItemAdapter.ItemAdapterVH(LayoutInflater.from(context).inflate(R.layout.item_transaction,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapterVH holder, int position) {

        ItemResponse itemResponse = itemResponseList.get(position);

        String itemDaymonth = itemResponse.getDaymonth();
        String itemMoney = itemResponse.getAmount();
        String itemCategory = itemResponse.getCategoryName();
        String itemDetail = itemResponse.getDescription();
        String itemType = itemResponse.getName();

        holder.itemCategory.setText(itemCategory);
        holder.itemDaymonth.setText(itemDaymonth);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedItem.ClickedTransactionItem(itemResponse);
            }
        });

        holder.itemDetail.setText(itemDetail);

        if (itemType.equals("Income")) {
            holder.itemMoney.setTextColor(ContextCompat.getColor(context, R.color.green));
            holder.itemMoney.setText("+ $" + itemMoney);
        }
        else {
            holder.itemMoney.setTextColor(ContextCompat.getColor(context, R.color.red));
            holder.itemMoney.setText("- $" + itemMoney);
        }

        if(itemCategory.equals("Housing")) {
            holder.icon.setImageResource(R.drawable.ic_home);
        }
        else if (itemCategory.equals("Food & Drink")) {
            holder.icon.setImageResource(R.drawable.ic_food);
        }
        else if (itemCategory.equals("Education")) {
            holder.icon.setImageResource(R.drawable.ic_education);
        }
        else if (itemCategory.equals("Transportation")) {
            holder.icon.setImageResource(R.drawable.ic_car);
        }
        else if (itemCategory.equals("Utilities")) {
            holder.icon.setImageResource(R.drawable.ic_computer);
        }
        else if (itemCategory.equals("Insurance")) {
            holder.icon.setImageResource(R.drawable.ic_pill);
        }
        else if (itemCategory.equals("Medical & Healthcare")) {
            holder.icon.setImageResource(R.drawable.ic_medical);
        }
        else if (itemCategory.equals("Investment")) {
            holder.icon.setImageResource(R.drawable.icon_investment);
        }
        else if (itemCategory.equals("Debt")) {
            holder.icon.setImageResource(R.drawable.icon_debt);
        }
        else if (itemCategory.equals("Personal")) {
            holder.icon.setImageResource(R.drawable.ic_people);
        }
        else if (itemCategory.equals("Entertainment")) {
            holder.icon.setImageResource(R.drawable.ic_entertainment);
        }
        else if (itemCategory.equals("Clothing")) {
            holder.icon.setImageResource(R.drawable.ic_clothes);
        }
        else if (itemCategory.equals("Salary")) {
            holder.icon.setImageResource(R.drawable.ic_salary);
        }
        else if (itemCategory.equals("Gift & Donation")) {
            holder.icon.setImageResource(R.drawable.icon_gift);
        }
        else if (itemCategory.equals("Loan")) {
            holder.icon.setImageResource(R.drawable.icon_loan);
        }
        else if(itemCategory.equals("Scholarship")) {
            holder.icon.setImageResource(R.drawable.ic_award);
        }
        else if (itemCategory.equals("Lottery")) {
            holder.icon.setImageResource(R.drawable.icon_lottery);
        }

    }

    @Override
    public int getItemCount() {
        return itemResponseList.size();
    }

    public class ItemAdapterVH extends RecyclerView.ViewHolder {

        TextView itemCategory;
        TextView itemDaymonth;
        TextView itemMoney;
        TextView itemDetail;
        ImageView icon;
        RelativeLayout relativeLayout;
        public ItemAdapterVH(@NonNull View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.today_report_icon_1);
            itemCategory = itemView.findViewById(R.id.today_report_name_1);
            itemDetail = itemView.findViewById(R.id.detail_today_report_1);
            itemDaymonth = itemView.findViewById(R.id.tv_hour_1);
            itemMoney = itemView.findViewById(R.id.today_report_money_1);
            relativeLayout = itemView.findViewById(R.id.today_report_1);

        }
    }


    public interface ClickedItem{
        public void ClickedTransactionItem(ItemResponse itemResponse);
    }
}
