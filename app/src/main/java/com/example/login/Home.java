package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.login.controllr.Drawer_Fragmen;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Context con;
    SharedPreferences sharedPreferences;
    Intent intent;
    ProgressDialog progressDialog;
    DrawerLayout drawerLayout;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Drawer_Fragmen()).commit();
                break;

            case R.id.logout:
                timerDelayRemoveDialog();
                break;

            case R.id.place_order:
                Intent intent1 = new Intent(this, Place_order.class);
                startActivity(intent1);
                break;

            case R.id.reg_cus:
                Intent intent2 = new Intent(this, Customer_register.class);
                startActivity(intent2);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);


        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        drawerLayout = findViewById(R.id.nav_view);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Drawer_Fragmen()).commit();

        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.u_name_txt);
        TextView greeting = (TextView) navigationView.getHeaderView(0).findViewById(R.id.greeting);
        sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        intent = new Intent(Home.this, MainActivity.class);
        txtProfileName.setText(sharedPreferences.getString("username", null));
        greeting.setText(getGreeting());
    }


    public static String getGreeting() {

        int hour = getHour();

        if (hour > 0 && hour < 12) {
            return "Good morning";
        } else if (hour == 12 || 12 > hour && hour < 2) {
            return "Good afternoon";
        } else {
            return "Good evening";
        }
    }
    private static int getHour() {
        Time now = new Time(Time.getCurrentTimezone());
        now.setToNow();
        return now.hour;
    }

    public void timerDelayRemoveDialog(){

        final Dialog d;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                progressDialog=new ProgressDialog(Home.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.prograss_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(Home.this, MainActivity.class);
                startActivity(intent);

            }
        }, 100);
    }

    @Override
    public void onBackPressed() {
        Dialog();
    }
    private void Dialog(){
        AlertDialog.Builder builder;
        builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Logout?");

        builder.setMessage("Do you want to Logout?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        intent = new Intent(Home.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog alertDialog=builder.create();
        alertDialog.setTitle("Logout");
        alertDialog.show();
    }
}