package com.example.android.services.post;

import com.example.android.dto.PostDto;

import java.io.IOException;
import java.util.List;

public interface PostService {
    List<PostDto> getAllPosts();
    PostDto addPost(PostDto postDto) throws IOException;
    PostDto updatePost(Long postId, PostDto postDto) throws IOException;
    boolean deletePost(Long id);
}
