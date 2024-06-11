package com.example.android.entity;
import com.example.android.dto.PostDto;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private Date createdAt;

    private Date updatedAt;


    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    public PostDto getPostDto(){
        PostDto postDto = new PostDto();
        postDto.setId(id);
        postDto.setContent(content);
        postDto.setByteImg(img);
        postDto.setCreatedAt(createdAt);
        postDto.setUpdatedAt(updatedAt);
        postDto.setUserId(user.getId());
        return postDto;
    }
}
