package com.example.login.server;

public class ApiUtils {
    public static final String BASE_URL = "https://raw.githubusercontent.com/rizlandev/TestPro/main/";

    public static UserService getUserService(){
        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }

}
