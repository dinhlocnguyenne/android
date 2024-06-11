package com.example.android.services.post;
import com.example.android.dto.PostDto;
import com.example.android.entity.Post;
import com.example.android.entity.User;
import com.example.android.repository.PostRepository;
import com.example.android.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final UserRepository userRepository;
    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postList = postRepository.findAll();
        return postList.stream().map(Post::getPostDto).collect(Collectors.toList());
    }
    @Override
    public PostDto addPost(PostDto postDto) throws IOException {
        Post post = new Post();
        post.setContent(postDto.getContent());
        post.setCreatedAt(postDto.getCreatedAt());
        post.setUpdatedAt(postDto.getUpdatedAt());
        if (postDto.getImg() != null) {
            post.setImg(postDto.getImg().getBytes());
        }
        User user = userRepository.findById(postDto.getUserId()).orElseThrow();
        post.setUser(user);
        return postRepository.save(post).getPostDto();
    }

    @Override
    public PostDto updatePost(Long postId, PostDto postDto) throws IOException {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Optional<User> optionalUser = userRepository.findById(postDto.getUserId());
        if (optionalPost.isPresent() && optionalUser.isPresent()) {
            Post post = optionalPost.get();
            post.setContent(postDto.getContent());
            post.setUpdatedAt(postDto.getUpdatedAt());
            if (postDto.getImg() != null) {
                post.setImg(postDto.getImg().getBytes());
            }
            return postRepository.save(post).getPostDto();
        }else {
            return null;
        }
    }
    @Override
    public boolean deletePost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            postRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
