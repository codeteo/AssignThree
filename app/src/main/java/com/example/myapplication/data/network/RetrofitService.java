package com.example.myapplication.data.network;

import com.example.myapplication.data.network.responses.BooksResponse;
import com.example.myapplication.data.network.responses.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("Login")
    Call<LoginResponse> login(@Field("UserName") String username, @Field("Password") String password);

    @GET("Books")
    Call<List<BooksResponse>> books(@Header("Authorization") String auth);

}
