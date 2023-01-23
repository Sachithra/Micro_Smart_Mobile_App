package com.example.login.modal;

import java.io.Serializable;

public class Orders implements Serializable {
    private int productId;
    private int price_id;
    private String productName;
    private double price;
    private String stocks;
    private String qty;
    private int categoryId;
    private String total;

    public Orders(String productName, double price, String stocks, String qty, String total,int productId,int categoryId,int price_id) {
        this.productName = productName;
        this.price = price;
        this.stocks = stocks;
        this.qty = qty;
        this.total = total;
        this.productId = productId;
        this.categoryId=categoryId;
        this.price_id=price_id;
    }

    public Orders() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getStocks() {
        return stocks;
    }

    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getPrice_id() {
        return price_id;
    }

    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }
}
