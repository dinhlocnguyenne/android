package com.example.android;

import android.content.Context;
import android.content.SharedPreferences;

public class UserStorage {
    private static final String SHARED_PREFS = "sharedPrefs";
    private static final String TOKEN_KEY = "token";
    private static final String USER_KEY = "user";
    private static final String USER_ID_KEY = "userId";

    private SharedPreferences sharedPreferences;

    public UserStorage(Context context) {
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }

    public void saveUser(AuthResponse user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_KEY, user.toJson());
        editor.putLong(USER_ID_KEY, user.getUserId());
        editor.apply();
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public AuthResponse getUser() {
        String userJson = sharedPreferences.getString(USER_KEY, null);
        if (userJson != null) {
            return AuthResponse.fromJson(userJson);
        }
        return null;
    }

    public Long getUserId() {
        return sharedPreferences.getLong(USER_ID_KEY, -1);
    }

    public void signOut() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        editor.remove(USER_KEY);
        editor.remove(USER_ID_KEY);
        editor.apply();
    }
}
