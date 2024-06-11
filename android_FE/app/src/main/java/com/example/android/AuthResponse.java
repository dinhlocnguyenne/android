package com.example.android;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("userId")
    private Long userId;

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    // các thuộc tính khác của bạn

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Các phương thức getter và setter khác

    public String toJson() {
        return new Gson().toJson(this);
    }

    public static AuthResponse fromJson(String json) {
        return new Gson().fromJson(json, AuthResponse.class);
    }
}
