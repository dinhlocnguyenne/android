package com.example.android.dto;
import java.util.Date;
import lombok.Data;


@Data
public class MessageDto {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private String status;
    private Date created;
}
