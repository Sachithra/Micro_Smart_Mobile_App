package com.example.login.controllr;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.login.local_db.SQLiteDatabaseHelper;
import com.example.login.modal.Categories;
import com.example.login.modal.Products;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Listdata_fruits {

        public static HashMap<String, ArrayList<Products>> getData(Context con, ArrayList<Products>
                tempList, int cat_id) {

                HashMap<String, ArrayList<Products>> expandableListDetail = new HashMap<>();
                ArrayList<Products> sub_cat= getCategoriesByCatId(con,cat_id);

                for(int i=0;i<sub_cat.size();i++){
                    String  se_sub_cat_name= sub_cat.get(i).getProductDescription();

                    ArrayList<Products> se_prices = new ArrayList<>();
                    for(int j=0;j<tempList.size();j++){
                    Products products=new Products();
                    products=tempList.get(j);
                    if(se_sub_cat_name.equalsIgnoreCase(products.getProductDescription())){
                        se_prices.add( new Products(products.getProductDescription(), products.
                                getPrice(),products.getProductId(),products.getProduct_stocks()
                                ,products.getPrice_id()));
                    }
                    }
                    expandableListDetail.put(se_sub_cat_name, se_prices);
                    }
                    return expandableListDetail;
                    }

            public static ArrayList<Products> getProductsByCatId(Context con, int catId){

            ArrayList<Products> productsList = new ArrayList<>();
            SQLiteDatabaseHelper dbIns = SQLiteDatabaseHelper.getDatabaseInstance(con);
            SQLiteDatabase db = dbIns.getReadableDatabase();

            Cursor cursor=db.rawQuery("SELECT product_description,product_price," +
                "PRODUCT_PRICES.product_id,Stocks,price_id FROM PRODUCTS" +
                " JOIN PRODUCT_PRICES ON PRODUCTS.product_id=PRODUCT_PRICES.product_id WHERE" +
                " category_id="+catId+"",null);

            while (cursor.moveToNext()){
            String description=cursor.getString(0);
            double product_price=cursor.getDouble(1);
            int product_id=cursor.getInt(2);
            int product_stocks=cursor.getInt(3);
            int price_id=cursor.getInt(4);
            Products product=new Products(description,product_price,product_id,product_stocks,price_id);

            productsList.add(product);
            }
            return productsList;
            }

            public static ArrayList<Products> getCategoriesByCatId(Context con, int catId){
            ArrayList<Products> productsList = new ArrayList<>();

            SQLiteDatabaseHelper dbIns = SQLiteDatabaseHelper.getDatabaseInstance(con);
            SQLiteDatabase db = dbIns.getReadableDatabase();

            Cursor cursor=db.rawQuery("SELECT product_id, product_description  FROM PRODUCTS WHERE" +
                " category_id="+catId+"",null);

            while (cursor.moveToNext()){
            int product_id=cursor.getInt(0);
            String description=cursor.getString(1);
            Products product=new Products(product_id,description);
            productsList.add(product);
            }
            return productsList;
            }
        }






