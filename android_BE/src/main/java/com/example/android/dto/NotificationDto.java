package com.example.android.dto;
import lombok.Data;
import java.util.Date;

@Data
public class NotificationDto {
    private Long id;
    private Long userId;
    private String content;
    private String status;
    private Date createdAt;
}
