package com.example.myapplication.data.network;

import com.example.myapplication.data.network.responses.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {

    @FormUrlEncoded
    @POST("Login")
    Call<LoginResponse> login(@Field("UserName") String username, @Field("Password") String password);

}
