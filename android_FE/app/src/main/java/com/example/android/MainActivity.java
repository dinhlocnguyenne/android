package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
public class MainActivity extends AppCompatActivity {

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = RetrofitClient.getClient("http://10.0.2.2:8080/");
        apiService = retrofit.create(ApiService.class);

        Button addPostButton = findViewById(R.id.add_post_button);
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddPostActivity.class);
                startActivity(intent);
            }
        });

        loadPosts();
    }

    private void loadPosts() {
        apiService.getAllPosts().enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    displayPosts(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load posts", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayPosts(List<Post> posts) {
        LinearLayout postsContainer = findViewById(R.id.posts_container);
        postsContainer.removeAllViews();

        for (Post post : posts) {
            LinearLayout postLayout = new LinearLayout(this);
            postLayout.setOrientation(LinearLayout.VERTICAL);
            postLayout.setPadding(8, 8, 8, 8);
            postLayout.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

            TextView usernameTextView = new TextView(this);
            usernameTextView.setText("Username: " + post.getUserId()); // You may need to fetch the username separately
            postLayout.addView(usernameTextView);

            TextView contentTextView = new TextView(this);
            contentTextView.setText(post.getContent());
            postLayout.addView(contentTextView);

            Button likeButton = new Button(this);
            likeButton.setText("Like");
            postLayout.addView(likeButton);

            Button commentButton = new Button(this);
            commentButton.setText("Comment");
            postLayout.addView(commentButton);

            postsContainer.addView(postLayout);
        }
    }
}