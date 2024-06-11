package com.example.android;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddPostActivity extends AppCompatActivity {
    private ApiService apiService;
    private UserStorage userStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        userStorage = new UserStorage(this);

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8080/");
        apiService = retrofit.create(ApiService.class);

        EditText postContentEditText = findViewById(R.id.post_content);
        Button addPostButton = findViewById(R.id.add_post_button);

        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = postContentEditText.getText().toString().trim();

                if (content.isEmpty()) {
                    Toast.makeText(AddPostActivity.this, "Please write something", Toast.LENGTH_SHORT).show();
                    return;
                }

                Long userId = userStorage.getUserId();
                if (userId == null || userId == -1) {
                    Toast.makeText(AddPostActivity.this, "User ID not found. Please log in again.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Post post = new Post();
                post.setUserId(userId);
                post.setContent(content);

                String token = userStorage.getToken();
                apiService.addPost("Bearer " + token, post).enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(Call<Post> call, Response<Post> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(AddPostActivity.this, "Post added successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.e("AddPost", "Response code: " + response.code());
                            Log.e("AddPost", "Response message: " + response.message());
                            Toast.makeText(AddPostActivity.this, "Failed to add post", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Post> call, Throwable t) {
                        Log.e("AddPost", "Error: " + t.getMessage());
                        Toast.makeText(AddPostActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
