package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    private ApiService apiService;
    private UserStorage userStorage;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userStorage = new UserStorage(this);

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8080/");
        apiService = retrofit.create(ApiService.class);

        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        TextView errorMessageTextView = findViewById(R.id.error_message);
        Button loginButton = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.register_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    errorMessageTextView.setText("Please fill out all fields");
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    errorMessageTextView.setText("Invalid email format");
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    return;
                }

                errorMessageTextView.setVisibility(View.GONE);

                AuthRequest authRequest = new AuthRequest(email, password);

                apiService.login(authRequest).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            AuthResponse authResponse = response.body();
                            String token = response.headers().get("Authorization").substring(7); // Lấy token từ header
                            userStorage.saveToken(token); // Lưu token vào SharedPreferences
                            userStorage.saveUser(authResponse); // Lưu thông tin user vào SharedPreferences

                            Log.d(TAG, "Bearer Token: " + token); // Log token ra để tiện kiểm tra bằng Postman

                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            errorMessageTextView.setText("Invalid email or password");
                            errorMessageTextView.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Log.e("API Error", t.getMessage());
                        errorMessageTextView.setText("An error occurred. Please try again.");
                        errorMessageTextView.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
