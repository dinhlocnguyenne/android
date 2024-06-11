package com.example.android.entity;
import com.example.android.dto.FriendDto;
import com.example.android.emuns.FriendStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "friend")
public class Friend {
    @EmbeddedId
    private FriendId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("friendId")
    @JoinColumn(name = "friend_id")
    private User friend;

    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private Date createdAt;

    public FriendDto getFriendDto(){
        FriendDto friendDto = new FriendDto();
        friendDto.setUserId(id.getUserId());
        friendDto.setFriendId(id.getFriendId());
        friendDto.setStatus(status.name());
        friendDto.setCreated(createdAt);
        return friendDto;
    }
}
