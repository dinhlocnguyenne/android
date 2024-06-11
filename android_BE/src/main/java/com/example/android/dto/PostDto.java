package com.example.android.dto;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import lombok.Data;


@Data
public class PostDto {
    private Long id;
    private String content;
    private Date createdAt;
    private Date updatedAt;

    private byte[] byteImg;
    private MultipartFile img;
    private Long userId;
}
