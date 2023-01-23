package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import com.example.login.controllr.Customer_controller;
import com.example.login.local_db.DBHandler;
import com.example.login.modal.Customers;

import java.util.ArrayList;

public class Customer_register extends AppCompatActivity {
 EditText cus_id,cus_uname,cus_phone,cus_age;
 Button reg_btn;
 CheckBox male,female;
 DBHandler dbHandler=new DBHandler(this);
 public String customer_gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_register);
        dbHandler.openDB();
        reg_btn=(Button)findViewById(R.id.reg_btn);
        cus_id=(EditText)findViewById(R.id.cus_id);
        cus_uname=(EditText)findViewById(R.id.cus_uname);
        cus_phone=(EditText)findViewById(R.id.cus_phone);
        cus_age=(EditText)findViewById(R.id.cus_age);
        male=(CheckBox)findViewById(R.id.male);
        female=(CheckBox)findViewById(R.id.female);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cus_id.getText().toString().isEmpty() || cus_uname.getText().toString().isEmpty()
                        || cus_phone.getText().toString().isEmpty() || cus_age.getText().toString()
                        .isEmpty() || male.getText().toString().isEmpty()
                        || female.getText().toString().isEmpty()) {

                    Toast.makeText(Customer_register.this,
                            "Pleases insert the values", Toast.LENGTH_SHORT).show();
                } else {


                    int ID = Integer.parseInt(cus_id.getText().toString());
                    String USERNAME = cus_uname.getText().toString();
                    String PHONE = cus_phone.getText().toString();
                    int AGE = Integer.parseInt(cus_age.getText().toString());

                    customer_gender = "";
                    if (male.isChecked())
                        customer_gender = "Male";
                    if (female.isChecked())
                        customer_gender = "Female";

                    ArrayList<Customers> customersArrayList = new ArrayList<>();
                    Customers customers = new Customers();
                    customers.setId(ID);
                    customers.setUsername(USERNAME);
                    customers.setPhone(PHONE);
                    customers.setAge(AGE);
                    customers.setGender(customer_gender);

                    customersArrayList.add(customers);
                    Toast.makeText(Customer_register.this,
                            "Customer Register Success", Toast.LENGTH_SHORT).show();

                    Customer_controller.insertCustomer(Customer_register.this, customersArrayList);
                    cus_id.getText().clear();
                    cus_uname.getText().clear();
                    cus_phone.getText().clear();
                    cus_age.getText().clear();
                }
            }
        });


    }
}