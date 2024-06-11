package com.example.android.entity;

import lombok.Data;

import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
@Data
public class FriendId implements Serializable {
    private Long userId;
    private Long friendId;
}