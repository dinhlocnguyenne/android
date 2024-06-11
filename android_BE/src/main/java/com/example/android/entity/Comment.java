package com.example.android.entity;
import com.example.android.dto.CommentDto;
import com.example.android.dto.PostDto;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name = "post")
public class Comment {
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId", nullable = false)
    private Post post;

    public CommentDto getCommentDto(){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(id);
        commentDto.setContent(content);
        commentDto.setByteImg(img);
        commentDto.setCreatedAt(createdAt);
        commentDto.setUpdatedAt(updatedAt);
        commentDto.setUserId(user.getId());
        commentDto.setPostId(post.getId());
        return commentDto;
    }
}
