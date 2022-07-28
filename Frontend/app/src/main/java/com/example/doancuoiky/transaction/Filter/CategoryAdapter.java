package com.example.doancuoiky.transaction.Filter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doancuoiky.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    View view;
    Context context;
    ArrayList<String> arrayList;
    CategoryListener categoryListener;

    ArrayList<String> arrayList_0 = new ArrayList<>();

    public CategoryAdapter(Context context, ArrayList<String> arrayList, CategoryListener categoryListener) {
        this.context = context;
        this.arrayList = arrayList;
        this.categoryListener = categoryListener;
    }

    public View getView() {
        return view;
    }

    public ArrayList<String> getArrayList_0() {
        return this.arrayList_0;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String temp = arrayList.get(position);
        if (arrayList != null && arrayList.size() > 0 ) {
            holder.checkBox.setText(temp);

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(holder.checkBox.isChecked()) {
                        arrayList_0.add(temp);
                    }
                    else {
                        arrayList_0.remove(temp);
                    }
                    categoryListener.onCategoryChange(arrayList_0);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkbox_category);
        }
    }
}
