package com.example.doancuoiky.budget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;

import java.util.List;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetAdapterVH> {
    private List<BudgetResponse> budgetResponseList;
    private Context context;
    private ClickedBudget clickedBudget;

    public BudgetAdapter(BudgetAdapter.ClickedBudget clickedBudget) {
        this.clickedBudget = clickedBudget;
    }

    public void setData(List<BudgetResponse> budgetResponseList) {
        this.budgetResponseList = budgetResponseList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BudgetAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new BudgetAdapter.BudgetAdapterVH(LayoutInflater.from(context).inflate(R.layout.item_budget,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetAdapterVH holder, int position) {
        BudgetResponse budgetResponse = budgetResponseList.get(position);

        String budgetName = budgetResponse.getBudgetName();
        String budgetAmount_Total = budgetResponse.getAmount();
        String budgetAmount_Spent = budgetResponse.getAmount_Spent();
        String budgetAmount_Remain = budgetResponse.getAmount_Remain();

        int total = new Integer(budgetAmount_Total).intValue();
        int spent = new Integer(budgetAmount_Spent).intValue();
        int remain = new Integer(budgetAmount_Remain).intValue();
        int percent = spent * 100 /total;

        holder.budgetName.setText(budgetName);
        holder.Amount_Remain.setText(budgetAmount_Remain);
        holder.Amount_Spent.setText(budgetAmount_Spent);
        holder.Amount_Total.setText(budgetAmount_Total);
        holder.progressRemain.setProgress(percent);

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedBudget.ClickedBudgetItem(budgetResponse);
            }
        });

        if (remain <= 0) {
            holder.warning.setVisibility(View.VISIBLE);
        }
        else {
            holder.warning.setVisibility(View.GONE);
        }

        if (budgetName.equals("Housing")){
            holder.progressRemain.setProgressTintList(ColorStateList.valueOf(Color.RED));
        }
        if (budgetName.equals("Food & Drink")){
            holder.progressRemain.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
        }
        if (budgetName.equals("Education")){
            holder.progressRemain.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        }
        if (budgetName.equals("Transportation")){
            holder.progressRemain.setProgressTintList(ColorStateList.valueOf(Color.GRAY));
        }
        if (budgetName.equals("Utilities")){
            holder.progressRemain.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
        }
        if (budgetName.equals("Insurance")){
            holder.progressRemain.setProgressTintList(ColorStateList.valueOf(Color.TRANSPARENT));
        }
        if (budgetName.equals("Medical & Healthcare")){
            holder.progressRemain.setProgressTintList(ColorStateList.valueOf(Color.CYAN));
        }
        if (budgetName.equals("Debt")){
            holder.progressRemain.setProgressTintList(ColorStateList.valueOf(Color.MAGENTA));
        }
        if (budgetName.equals("Clothing")){
            holder.progressRemain.setProgressTintList(ColorStateList.valueOf(Color.LTGRAY));
        }
        if (budgetName.equals("Personal")){
            holder.progressRemain.setProgressTintList(ColorStateList.valueOf(Color.DKGRAY));
        }
    }

    @Override
    public int getItemCount() {
        return budgetResponseList.size();
    }

    public class BudgetAdapterVH extends RecyclerView.ViewHolder {
        TextView budgetName;
        TextView Amount_Total;
        TextView Amount_Spent;
        TextView Amount_Remain;
        ProgressBar progressRemain;
        LinearLayout linearLayout;
        ImageView warning;
        public BudgetAdapterVH(@NonNull View itemView) {
            super(itemView);
            budgetName = itemView.findViewById(R.id.budget_name);
            Amount_Remain= itemView.findViewById(R.id.amount_remain);
            Amount_Spent = itemView.findViewById(R.id.amount_spent);
            Amount_Total = itemView.findViewById(R.id.budget_amount);
            progressRemain = itemView.findViewById(R.id.progressBar1);
            linearLayout = itemView.findViewById(R.id.item_budget);
            warning = itemView.findViewById(R.id.warning);
        }
    }

    public interface ClickedBudget{
        public void ClickedBudgetItem(BudgetResponse budgetResponse);
    }
}
