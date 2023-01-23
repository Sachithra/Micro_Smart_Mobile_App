package com.example.login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.login.controllr.Product_controller;
import com.example.login.local_db.DBHandler;
import com.example.login.modal.Products;
import com.example.login.modal.ResService;
import com.example.login.server.ApiUtils;
import com.example.login.server.UserService;
import com.google.androidgamesdk.gametextinput.Listener;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MainActivity extends AppCompatActivity {

    Button login_btn;
    EditText passwordET, u_nameET;
    CheckBox show_password;
    UserService userService;
    SharedPreferences sharedPreferences;
    private ProgressBar progressBar;
    private int progressStatus;
    private Handler handler=new Handler();
    Intent intent;

    private DBHandler dbHandler=new DBHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler.openDB();

        show_password=(CheckBox)findViewById(R.id.show_password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        u_nameET = (EditText) findViewById(R.id.u_name);
        passwordET = (EditText) findViewById(R.id.passwod);
        login_btn = (Button) findViewById(R.id.login_btn);
        userService = ApiUtils.getUserService();

        sharedPreferences = getSharedPreferences("user_details",MODE_PRIVATE);
        intent = new Intent(MainActivity.this,Home.class);
        if(sharedPreferences.contains("username") && sharedPreferences.contains("password")) {
            startActivity(intent);
        }

        /**
         * Hide and show password
         */
        show_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!isChecked){
                    passwordET.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else {
                    passwordET.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            /**
             * LOGIN BUTTON
             */
            String product_Cat_json="";
            String price_json="";
            String product_json="";
            @Override
            public void onClick(View v) {
                String username = u_nameET.getText().toString();
                String password = passwordET.getText().toString();

                if (validateLogin(username, password)) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgressTintList(ColorStateList.valueOf(Color.WHITE));
                            try {
                                URL url_product_category=new URL("https://raw.githubusercontent." +
                                        "com/rizlandev/TestPro/main/product_categories");

                                //price_url
                                URL url_products_prices=new URL("https://raw.githubusercontent.com" +
                                        "/rizlandev/TestPro/main/prices");

                                URL url_products=new URL("https://raw.githubusercontent.com" +
                                        "/rizlandev/TestPro/main/products");

                                HttpURLConnection httpURLConnection= (HttpURLConnection)
                                        url_product_category.openConnection();

                                HttpURLConnection httpURLConnection_products= (HttpURLConnection)
                                        url_products.openConnection();
                                InputStream inputStream_products=httpURLConnection_products.getInputStream();

                                BufferedReader bufferedReader_products=new BufferedReader(
                                        new InputStreamReader(inputStream_products));

                                //price
                                HttpURLConnection httpURLConnection1= (HttpURLConnection)
                                        url_products_prices.openConnection();
                                InputStream inputStream=httpURLConnection.getInputStream();

                                //price
                                InputStream inputStream1=httpURLConnection1.getInputStream();

                                BufferedReader bufferedReader=new BufferedReader(
                                        new InputStreamReader(inputStream));

                                //Price
                                BufferedReader bufferedReader1=new BufferedReader
                                        (new InputStreamReader(inputStream1));
                                String line;

                                while((line=bufferedReader.readLine()) !=null){
                                    product_Cat_json=product_Cat_json+line;
                                }
                                while ((line = bufferedReader_products.readLine()) != null) {
                                    product_json=product_json+line;
                                }

                                //Price
                                while((line=bufferedReader1.readLine()) !=null){
                                    price_json=price_json+line;
                                }
                                if(!product_Cat_json.isEmpty()){
                                    ArrayList<Products> products_categories_ArrayList = new ArrayList<>();
                                    JSONObject jsonObject=new JSONObject(product_Cat_json);
                                    JSONArray productCat = jsonObject.getJSONArray("product_cat");



                                    for (int i = 0; i < productCat.length(); i++) {

                                        JSONObject jsonObjects = productCat.getJSONObject(i);

                                        int id = Integer.parseInt(jsonObjects.optString("pro_cat_id"));
                                        String name = jsonObjects.optString("cat_description");
                                        Products pro=new Products();
                                        pro.setProductId(id);
                                        pro.setProductName(name);

                                        products_categories_ArrayList.add(pro);

                                    }

                                    Product_controller.insertProduct_categories(MainActivity.
                                            this,products_categories_ArrayList);

                                    ArrayList<Products> products_price = new ArrayList<>();
                                    JSONObject prices_data=new JSONObject(price_json);


                                    JSONArray pricesInfo = prices_data.getJSONArray("prices_data");


                                    for (int i = 0; i < pricesInfo.length(); i++) {

                                        JSONObject pricesData = pricesInfo.getJSONObject(i);
                                        int pro_id = Integer.parseInt(pricesData.optString("pro_id"));
                                        JSONArray priceList = pricesData.getJSONArray("prices");

                                        for (int j = 0; j < priceList.length(); j++) {

                                            JSONObject proPrices = priceList.getJSONObject(j);

                                            int price_id = Integer.parseInt(proPrices.optString("price_id"));
                                            double price = Double.parseDouble(proPrices.optString("price"));
                                            Products pro = new Products();
                                            pro.setProductId(pro_id);
                                            pro.setPrice_id(price_id);
                                            pro.setPrice(price);
                                            pro.setProduct_stocks(100);

                                            products_price.add(pro);
                                        }
                                    }
                                    Product_controller.insertProducts_prices
                                            (MainActivity.this,products_price);

                                    }


                                if (!product_json.isEmpty()){
                                    ArrayList<Products> productsArrayList = new ArrayList<>();
                                    JSONObject jsonObject_product=new JSONObject(product_json);
                                    JSONArray productData = jsonObject_product.getJSONArray("product_data");

                                    for (int i = 0; i < productData.length(); i++) {

                                        JSONObject jsonObjects_products = productData.getJSONObject(i);

                                        int id_product = Integer.parseInt(jsonObjects_products.optString("pro_id"));
                                        String description = jsonObjects_products.optString("pro_description");
                                        int id_cat = Integer.parseInt(jsonObjects_products.optString("pro_cat_id"));
                                        Products pro=new Products();

                                        pro.setProductId(id_product);
                                        pro.setProductDescription(description);
                                        pro.setCategoryId(id_cat);

                                        productsArrayList.add(pro);

                                    }

                                    Product_controller.insertProducts(MainActivity.
                                            this,productsArrayList);

                                }

                                    } catch (MalformedURLException e) {
                                     e.printStackTrace();
                                    } catch (IOException e) {
                                    e.printStackTrace();
                                    } catch (JSONException e) {
                                    e.printStackTrace();
                                    }

                                while(progressStatus <100){
                                progressStatus+=1;
                                handler.post(new Runnable(){
                                    public void run(){
                                        progressBar.setProgress(progressStatus);
                                        if(progressStatus==100){
                                            doLogin(username, password);
                                        }
                                    }
                                });
                                try{
                                    Thread.sleep(200);
                                }catch (InterruptedException e){
                                    e.printStackTrace();
                                }
                                }
                                }
                                }).start();

                            }
                        }
                    });
                }

    private boolean validateLogin (String username, String password){
        if (username == null || username.trim().length() == 0) {
            Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == null || password.trim().length() == 0) {
            Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doLogin (final String username, final String password){

        Call<ResService> call = userService.login(username, password);
        call.enqueue(new Callback<ResService>() {

            @Override
            public void onResponse(Call<ResService> call, Response<ResService> response) {
                if (response.isSuccessful()) {
                    ResService resObj = response.body();

                    if (resObj.isResult()==true) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username",username);
                        editor.putString("password",password);
                        editor.commit();

                        Toast.makeText(getApplicationContext(), "Login Successful",
                                Toast.LENGTH_SHORT).show();
                        startActivity(intent);

                    } else {
                        Toast.makeText(MainActivity.this, "The username or password" +
                                " is incorrect", Toast.LENGTH_SHORT).show();
                    }
                    } else {
                    Toast.makeText(MainActivity.this, "Error! Please try again!",
                            Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<ResService> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT)
                        .show();
                }
            });
        }

    }

