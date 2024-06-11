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

public class RegisterActivity extends AppCompatActivity {

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8080/");
        apiService = retrofit.create(ApiService.class);

        EditText nameEditText = findViewById(R.id.name);
        EditText emailEditText = findViewById(R.id.email);
        EditText passwordEditText = findViewById(R.id.password);
        EditText confirmPasswordEditText = findViewById(R.id.confirm_password);
        TextView errorMessageTextView = findViewById(R.id.error_message);
        Button registerButton = findViewById(R.id.register_button);
        Button loginButton = findViewById(R.id.login_button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEditText.getText().toString().trim();
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    errorMessageTextView.setText("Please fill out all fields");
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    errorMessageTextView.setText("Invalid email format");
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    errorMessageTextView.setText("Passwords do not match");
                    errorMessageTextView.setVisibility(View.VISIBLE);
                    return;
                }

                errorMessageTextView.setVisibility(View.GONE);

                User newUser = new User();
                newUser.setName(name);
                newUser.setEmail(email);
                newUser.setPassword(password);

                apiService.register(newUser).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Log.e("API Error", t.getMessage());
                    }
                });
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
