package com.example.login.controllr;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.example.login.local_db.DBHandler;
import com.example.login.local_db.SQLiteDatabaseHelper;
import com.example.login.modal.Customers;

import java.util.ArrayList;

public class Customer_controller {

    public static void insertCustomer(Context con, ArrayList<Customers> customers){
        SQLiteDatabaseHelper databaseHelper = SQLiteDatabaseHelper.getDatabaseInstance(con);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();
        try {
            database.beginTransaction();


            String insertAssetQuery = "INSERT INTO CUSTOMERS (customer_id,username,phone,age,gender) values(?,?,?,?,?)";

            SQLiteStatement assetStatement = database.compileStatement(insertAssetQuery);

            for (Customers cus : customers) {
                DBHandler.performExecuteInsert(assetStatement, new Object[]{
                        cus.getId(),cus.getUsername(),cus.getPhone(),cus.getAge(),cus.getGender()
                });

            }
            database.setTransactionSuccessful();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            database.endTransaction();
            databaseHelper.close();
        }
    }
}
