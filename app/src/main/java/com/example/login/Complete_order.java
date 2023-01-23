package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login.modal.Orders;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Complete_order extends AppCompatActivity {
    ArrayList<Orders>getOrderList;
    double tot_amt=0.0;
    Button payment_btn;
    ArrayList<String>orders_items=new ArrayList<>();
    TextView od_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_order);
        od_details=(TextView)findViewById(R.id.od_pro_data);
        payment_btn=(Button)findViewById(R.id.payment_btn);
        DateFormat df = new SimpleDateFormat("KK:mm:ss a",
                Locale.getDefault());
        DateFormat date = new SimpleDateFormat( "dd/MM/yyyy",
                Locale.getDefault());
        String currentTime = df.format(new Date());
        String currentDate = date.format(new Date());

        getOrderList=(ArrayList<Orders>) getIntent().getSerializableExtra("od_details");
        StringBuilder Main_stringBuilder=new StringBuilder();

        Main_stringBuilder.append(getPaddedString((getCenterString ("INVOICE", 66))));
        Main_stringBuilder.append(padLeft("TEL-24859641",37)).append("\n");
        Main_stringBuilder.append(padLeft("MICRO SUPER",36)).append("\n").append("\n");
        Main_stringBuilder.append(padLeft("",42)+"Time:"+currentTime).append("\n");
        Main_stringBuilder.append(padLeft("",42)+"Date:"+currentDate).append("\n").append("\n");

        Main_stringBuilder.append(padLeft("",5)+"Thank you,\n");
        Main_stringBuilder.append(padLeft("",5)+"No 80,\n"+padLeft("",5) +
                "LS Street,\n"+padLeft("",5) + "Colombo-04.").append("\n");
        Main_stringBuilder.append("\n");
        Main_stringBuilder.append("\n");

        Main_stringBuilder.append(padLeft("",4)+"DESCRIPTION \n");
        Main_stringBuilder.append(padLeft("",4)+"------------------------------------------\n");

        Main_stringBuilder.append(padLeft("",5)+"Item"+padLeft("",46)+"Tot"+"*").append("\n");
        Main_stringBuilder.append("\n");


       for (int i=0;i<getOrderList.size();i++){
           Orders orders=new Orders();
           orders=getOrderList.get(i);

           int qty = Integer.parseInt(orders.getQty());
           double price =orders.getPrice();
           double item_val = qty * price;
           tot_amt =tot_amt+item_val;
           Main_stringBuilder.append( leftPadding(orders.getProductName(),' ',11)
                   +leftPadding(orders.getPrice(),' ',11)
                   +leftPadding(orders.getQty(),' ',11)+ padLeft(" ",9)
                   +leftPadding(orders.getTotal(),' ',11) );
           Main_stringBuilder.append("\n");
           }
           Main_stringBuilder.append(padLeft("",4)+"------------------------------------------\n");
           Main_stringBuilder.append(padLeft("",36)+"AMOUNT RS:"+" "+tot_amt);
           Main_stringBuilder.append(padLeft("",4)+"------------------------------------------\n");
           Main_stringBuilder.append("\n");
           Main_stringBuilder.append(getPaddedString((getCenterString ("****", 74))));

           String display=Main_stringBuilder.toString();
           od_details.setText(display);


           payment_btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Toast.makeText(Complete_order.this, "Clicked", Toast.LENGTH_SHORT).show();
                   Intent intent=new Intent(Complete_order.this,Payment.class);
                   intent.putExtra("total",tot_amt);
                   startActivity(intent);
               }
           });

           }

             public static String padRight(String s, int n) {
             return String.format("%-" + n + "s", s);
             }

            public static String leftPadding(Object input, char ch, int L) {
            String result = String.format("%" + L + "s", input).replace(' ', ch);
            return result;
            }

            private String getPaddedString(String snippet) {
            return snippet + "\n";
            }

            public static String padLeft(String s, int n) {
            return String.format("%" + n + "s", s);
            }
            private String getCenterString(String text, int length) {

               if (text.length() >= length) {
                   return text.substring(0, length - 2) + "..\n";
               } else {
                   int startPosition = (length - text.length()) / 2;
                   StringBuilder builder = new StringBuilder();
                   for (int i = 0; i < startPosition; i++) {
                       builder.append(" ");
                   }
                   builder.append(text);
                   return builder.toString();
               }
           }





        @Override
        public void onBackPressed() {
        Intent intent = new Intent(Complete_order.this, Place_order.class);
        intent.putExtra("od_details", getOrderList);
        startActivity(intent);
    }
}