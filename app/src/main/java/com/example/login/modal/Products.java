package com.example.login.modal;

public class Products {
    private int categoryId;
    private int productId;
    private String productName;
    private String productDescription;
    private double price;
    private int product_stocks;
    private int price_id;

    public Products() {
    }

    public Products(String productDescription, double price,int productId,int product_stocks,int price_id) {
        this.productDescription = productDescription;
        this.price = price;
        this.productId=productId;
        this.product_stocks=product_stocks;
        this.price_id=price_id;
    }

    public Products(int productId,String productDescription) {

        this.productId=productId;
        this.productDescription = productDescription;

    }
    public Products(String productName) {
        this.productName = productName;
    }


    public Products(int productId,int cat_id) {
        this.categoryId = cat_id;
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPrice_id() {
        return price_id;
    }

    public void setPrice_id(int price_id) {
        this.price_id = price_id;
    }

    public int getProduct_stocks() {
        return product_stocks;
    }

    public void setProduct_stocks(int product_stocks) {
        this.product_stocks = product_stocks;
    }

    @Override
    public String toString() {
        return productDescription;

    }

}
