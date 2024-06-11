package com.example.android.entity;

import com.example.android.dto.MessageDto;
import com.example.android.dto.NotificationDto;
import com.example.android.emuns.NotificationStatus;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "Notification")
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;
    public NotificationDto getNotificationDto(){
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setId(id);
        notificationDto.setUserId(user.getId());
        notificationDto.setContent(content);
        notificationDto.setStatus(status.name());
        notificationDto.setCreatedAt(createdAt);
        return notificationDto;
    }
}
