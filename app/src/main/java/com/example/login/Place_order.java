package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.example.login.controllr.DataTransfer;
import com.example.login.controllr.ExpandableList_Adaptor;
import com.example.login.controllr.ViewPage_Adaptor;
import com.example.login.local_db.DBHandler;
import com.example.login.modal.Orders;
import com.example.login.modal.Products;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.HashMap;

public class Place_order extends AppCompatActivity implements DataTransfer {
    
    DBHandler dbHandler=new DBHandler(this);
    ArrayList<Products>product_cat=new ArrayList<>();
    ArrayList<Orders> order_data=new ArrayList<>();
    ExpandableListAdapter expandableList_Adapter;

    ArrayList<Orders> getDetails(){
        return order_data;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {

                dbHandler.openDB();
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.
                LayoutParams.FLAG_FULLSCREEN);
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_place_order1);

                Bundle extras = this.getIntent().getExtras();
                if (extras != null) {
                    order_data = (ArrayList<Orders>)this.getIntent().getSerializableExtra("od_details");
                }
                Button complete_btn=findViewById(R.id.complete_btn);
                TabLayout tabLayout=findViewById(R.id.tabs);
                ViewPager2 viewPager2=findViewById(R.id.view_page);
                product_cat = dbHandler.get_categories();

                expandableList_Adapter=new ExpandableList_Adaptor(Place_order.this,
                        this,order_data);
                complete_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                expandableList_Adapter.areAllItemsEnabled();
                Intent intent=new Intent(Place_order.this, Complete_order.class);
                intent.putExtra("od_details",order_data);
                startActivity(intent);
                }
                });

                ViewPage_Adaptor viewPage_adaptor=new ViewPage_Adaptor(this,order_data);
                viewPager2.setAdapter(viewPage_adaptor);
                new TabLayoutMediator(tabLayout,viewPager2,
                        new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        Products products=product_cat.get(position);
                        tab.setText(products.getProductName());
                    }
                    }).attach();
                    }

                @Override
                public void onBackPressed() {
                Dialog();
        }
                private void Dialog(){

                     AlertDialog.Builder builder;
                     builder=new AlertDialog.Builder(this);
                     builder.setMessage("Do you want to Close order?");
                     builder.setMessage("Do you want to Close order?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(Place_order.this,Home.class);
                        startActivity(intent);
                        finish();
                    }
                    })

                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                    });
                    AlertDialog alertDialog=builder.create();
                    alertDialog.setTitle("Close Order");
                    alertDialog.show();
                }

            @Override
            public void onSetValues(ArrayList<Orders> order_data) {
            this.order_data=order_data;
        }
    }