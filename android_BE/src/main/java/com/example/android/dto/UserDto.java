package com.example.android.dto;

import com.example.android.emuns.UserRole;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String name;
    private UserRole role;

    private byte[] byteImg;

    private MultipartFile img;

}
