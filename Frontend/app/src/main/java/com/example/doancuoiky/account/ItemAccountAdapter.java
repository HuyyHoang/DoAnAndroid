package com.example.doancuoiky.account;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.doancuoiky.R;

import java.lang.reflect.Field;
import java.util.List;

public class ItemAccountAdapter extends ArrayAdapter<ItemAccountResponse> {
    private String KEY_ACCOUNT;
    public ItemAccountAdapter(@NonNull Context context, int resource, @NonNull List<ItemAccountResponse> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SharedPreferences sharedPreferences_3;
        String SHARED_PREF_NAME_3 = "ACCOUNT";



        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_account, null, false);
        }

        sharedPreferences_3 = getContext().getSharedPreferences(SHARED_PREF_NAME_3, MODE_PRIVATE);
        String name = sharedPreferences_3.getString(KEY_ACCOUNT, null);
        ItemAccountResponse account = getItem(position);
        ImageView image_active_user = (ImageView) convertView.findViewById(R.id.image_active_user);
        TextView tvAccountName = (TextView) convertView.findViewById(R.id.tv_account);
        TextView money = (TextView) convertView.findViewById(R.id.tv_amount);

        if (account != null) {
            tvAccountName.setText(account.getName());

            if (name != null && name.equals(account.getName())) {
                image_active_user.setVisibility(View.VISIBLE);
                money.setVisibility(View.GONE);
            }
            else {
                image_active_user.setVisibility(View.GONE);
                money.setVisibility(View.VISIBLE);
                money.setText("$" + String.valueOf(account.getAmount()));
            }

            ImageView icon = (ImageView) convertView.findViewById(R.id.account_icon);

            if (account.getName().equals("ACB")) {
                icon.setImageResource(R.drawable.logo_acb);
            }
            else if (account.getName().equals("BIDV")) {
                icon.setImageResource(R.drawable.logo_bidv);
            }
            else if (account.getName().equals("Vietinbank")) {
                icon.setImageResource(R.drawable.logo_vietinbank);
            }
            else if (account.getName().equals("MB")) {
                icon.setImageResource(R.drawable.logo_mb);
                icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            else if (account.getName().equals("ABBank")) {
                icon.setImageResource(R.drawable.logo_abbank);
                icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            else if (account.getName().equals("Wallet")) {
                icon.setImageResource(R.drawable.logo_wallet);
                icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            else if (account.getName().equals("Paypal")) {
                icon.setImageResource(R.drawable.logo_paypal);
                icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            else if (account.getName().equals("TPBank")) {
                icon.setImageResource(R.drawable.logo_tpbank);
            }
            else if (account.getName().equals("DongA Bank")) {
                icon.setImageResource(R.drawable.logo_donga_bank);
                icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            else if (account.getName().equals("BacABank")) {
                icon.setImageResource(R.drawable.logo_baca_bank);
                icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            else if (account.getName().equals("SeABank")) {
                icon.setImageResource(R.drawable.logo_sea_bank);
                icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            else if (account.getName().equals("MSB")) {
                icon.setImageResource(R.drawable.logo_msb);
            }
            else if (account.getName().equals("Techcombank")) {
                icon.setImageResource(R.drawable.logo_techcombank);
                icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            else if (account.getName().equals("Sacombank")) {
                icon.setImageResource(R.drawable.logo_sacombank);
                icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            else if (account.getName().equals("NamABank")) {
                icon.setImageResource(R.drawable.logo_nama_bank);
            }
            else if (account.getName().equals("VCB")) {
                icon.setImageResource(R.drawable.logo_vcb);
            }
            else if (account.getName().equals("HDBank")) {
                icon.setImageResource(R.drawable.logo_hdbank);
                icon.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            else if (account.getName().equals("OCB")) {
                icon.setImageResource(R.drawable.logo_ocb);
            }
            else if (account.getName().equals("PVcombank")) {
                icon.setImageResource(R.drawable.logo_pvcombank);
                icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            else if (account.getName().equals("VPBank")) {
                icon.setImageResource(R.drawable.logo_vpbank);
                icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }



        return convertView;
    }
}
