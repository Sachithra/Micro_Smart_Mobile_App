package com.example.login.controllr;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.login.Complete_order;
import com.example.login.Fruits_Fragment;
import com.example.login.Groceries_Fragment;
import com.example.login.Vegitables_Fragment;
import com.example.login.local_db.DBHandler;
import com.example.login.modal.Orders;
import com.example.login.modal.Products;

import java.util.ArrayList;

public class ViewPage_Adaptor extends FragmentStateAdapter {

    ArrayList<Orders>od_items ;

    public ViewPage_Adaptor(@NonNull FragmentActivity fragmentActivity,ArrayList<Orders>od_items) {
        super(fragmentActivity);
        this.od_items=od_items;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){

            case 0:
                return new Fruits_Fragment();
            case 1:
                return new Groceries_Fragment();
            case 2:
                return new Vegitables_Fragment();
        }
        return null;
    }
    @Override
    public int getItemCount() {
        return 3;
    }
}