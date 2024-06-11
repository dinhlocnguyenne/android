package com.example.android;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    //Login
    @Headers("Content-Type: application/json")
    @POST("authenticate")
    Call<AuthResponse> login(@Body AuthRequest authRequest);

    @Headers("Content-Type: application/json")
    @POST("sign-up")
    Call<AuthResponse> register(@Body User user);

    //Post
    @POST("api/post/add-post")
    Call<Post> addPost(@Header("Authorization") String token, @Body Post post);

    @PUT("api/post/update-post/{id}")
    Call<Post> updatePost(@Header("Authorization") String token, @Path("id") Long id, @Body Post post);

    @DELETE("api/post/{id}")
    Call<Void> deletePost(@Path("id") Long id);

    @GET("api/post/all")
    Call<List<Post>> getAllPosts();
}