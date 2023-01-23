package com.example.login.server;



import com.example.login.modal.Categories;
import com.example.login.modal.ResService;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface UserService {

    @GET("login?")
    Call<ResService> login(@Query("username") String username, @Query("password") String password);

}
