package com.example.login.controllr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.login.R;
import com.example.login.modal.Orders;

import java.util.ArrayList;

public class Order_Adaptor extends ArrayAdapter<Orders> {

    public Order_Adaptor(@NonNull Context context, ArrayList<Orders> order_arrayList) {
        super(context,0, order_arrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View currentItemView = convertView;


        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate
                    (R.layout.custom_list_orders, parent, false);
        }

        Orders currentNumberPosition = getItem(position);

        TextView namep_txt = currentItemView.findViewById(R.id.namep_txt);
        namep_txt.setText(currentNumberPosition.getProductName());

        TextView pricep_txt = currentItemView.findViewById(R.id.pricep_txt);
        pricep_txt.setText(String.valueOf(currentNumberPosition.getPrice()));

        TextView qtyp_txt = currentItemView.findViewById(R.id.qtyp_txt);
        qtyp_txt.setText(currentNumberPosition.getQty());

        TextView totp_txt = currentItemView.findViewById(R.id.totp_txt);
        totp_txt.setText(currentNumberPosition.getTotal());


        return currentItemView;
    }


}
