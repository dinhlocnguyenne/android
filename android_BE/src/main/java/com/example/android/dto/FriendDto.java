package com.example.android.dto;

import lombok.Data;

import java.util.Date;

@Data
public class FriendDto {
    private Long userId;
    private Long friendId;
    private String status;
    private Date created;
}
