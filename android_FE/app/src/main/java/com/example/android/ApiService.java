package com.example.android;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @POST("authenticate")
    @Headers("Content-Type: application/json")
    Call<AuthResponse> login(@Body User user);

    @POST("sign-up")
    @Headers("Content-Type: application/json")
    Call<AuthResponse> register(@Body User user);
}