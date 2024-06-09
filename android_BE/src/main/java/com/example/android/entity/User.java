package com.example.android.entity;

import com.example.android.dto.UserDto;
import com.example.android.emuns.UserRole;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String password;

    private String name;

    private String email;

    private UserRole role;
    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;


    public UserDto getUserDto(){
        UserDto userDto = new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setRole(role);
        userDto.setEmail(email);
        return userDto;
    }
}
