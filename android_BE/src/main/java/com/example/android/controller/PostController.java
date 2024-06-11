package com.example.android.controller;

import com.example.android.dto.PostDto;
import com.example.android.services.post.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    @PutMapping("/post/update-post/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id, @ModelAttribute PostDto postDto) throws IOException {
        PostDto postDto1 = postService.updatePost(id, postDto);
        return ResponseEntity.status(HttpStatus.OK).body(postDto1);
    }
    @DeleteMapping("/post/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        boolean deleted = postService.deletePost(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/post/add-post")
    public ResponseEntity<PostDto> addPost(@RequestBody PostDto postDto) throws IOException {
        PostDto postDto1 = postService.addPost(postDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto1);
    }
}
