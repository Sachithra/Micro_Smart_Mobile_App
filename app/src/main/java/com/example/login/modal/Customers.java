package com.example.login.modal;

import java.io.Serializable;

public class Customers implements Serializable {
    private  int id;
    private String username;
    private String phone;
    private int age;
    private String gender;


    public Customers() {
    }

    public Customers(int id, String username, String phone, int age,String gender) {
        this.id = id;
        this.username = username;
        this.phone = phone;
        this.age = age;
        this.gender=gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
