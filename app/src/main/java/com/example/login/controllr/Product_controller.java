package com.example.login.controllr;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import com.example.login.local_db.SQLiteDatabaseHelper;
import com.example.login.local_db.DBHandler;
import com.example.login.modal.Products;
import java.util.ArrayList;

public class Product_controller {

    public static void insertProduct_categories(Context con, ArrayList<Products> products){
            SQLiteDatabaseHelper databaseHelper = SQLiteDatabaseHelper.getDatabaseInstance(con);
            SQLiteDatabase database = databaseHelper.getWritableDatabase();
            try {
            database.beginTransaction();

            String insertAssetQuery = "replace into PRODUCTS_CATEGORIES " +
                    "(product_id,product) values(?,?)";
            SQLiteStatement assetStatement = database.compileStatement(insertAssetQuery);
            for (Products pro : products) {
                DBHandler.performExecuteInsert(assetStatement, new Object[]{
                        pro.getProductId(),pro.getProductName()
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


    public static void insertProducts(Context con, ArrayList<Products> products){
            SQLiteDatabaseHelper databaseHelper = SQLiteDatabaseHelper.getDatabaseInstance(con);
            SQLiteDatabase database = databaseHelper.getWritableDatabase();
            try {
            database.beginTransaction();
            String insertAssetQuery = "replace into PRODUCTS " +
                    "(product_id,product_description,category_id) values(?,?,?)";
            SQLiteStatement assetStatement = database.compileStatement(insertAssetQuery);
            for (Products pro : products) {
                DBHandler.performExecuteInsert(assetStatement, new Object[]{
                        pro.getProductId(),pro.getProductDescription(),pro.getCategoryId()
                });
            }
            database.setTransactionSuccessful();
            }catch (SQLException ex) {
            ex.printStackTrace();
            }finally {
            database.endTransaction();
            databaseHelper.close();
        }
    }
    public static void insertProducts_prices(Context con, ArrayList<Products> products){

                SQLiteDatabaseHelper databaseHelper = SQLiteDatabaseHelper.getDatabaseInstance(con);
                SQLiteDatabase database = databaseHelper.getWritableDatabase();
                try {
                database.beginTransaction();
                String insertAssetQuery = "replace into PRODUCT_PRICES" +
                        "(product_id,price_id,product_price,stocks) values(?,?,?,?)";
                SQLiteStatement assetStatement = database.compileStatement(insertAssetQuery);
                for (Products pro : products) {
                    DBHandler.performExecuteInsert(assetStatement, new Object[]{
                            pro.getProductId(), pro.getPrice_id(), pro.getPrice(), pro.getProduct_stocks()
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
