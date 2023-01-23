package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.login.modal.Orders;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {
    TextView amount_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                ,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        amount_txt=(TextView)findViewById(R.id.bill_amount);

        Bundle extras = this.getIntent().getExtras();
        if (extras != null) {
            Intent intent = getIntent();
            String tot_str = intent.getStringExtra("total");
            amount_txt.setText(tot_str);
        }

    }
}