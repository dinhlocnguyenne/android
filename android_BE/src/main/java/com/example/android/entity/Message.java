    package com.example.android.entity;

    import com.example.android.dto.MessageDto;
    import com.example.android.dto.PostDto;
    import com.example.android.emuns.MessageStatus;
    import lombok.Data;
    import org.hibernate.annotations.CreationTimestamp;
    import jakarta.persistence.*;

    import java.util.Date;

    @Entity
    @Table(name = "Message")
    @Data
    public class Message {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "sender_id")
        private User sender;

        @ManyToOne
        @JoinColumn(name = "receiver_id")
        private User receiver;

        @Lob
        private String content;

        @Enumerated(EnumType.STRING)
        private MessageStatus status;

        @Column(name = "created_at", updatable = false)
        @CreationTimestamp
        private Date createdAt;

        public MessageDto getMessageDto(){
            MessageDto messageDto = new MessageDto();
            messageDto.setId(id);
            messageDto.setContent(content);
            messageDto.setSenderId(sender.getId());
            messageDto.setReceiverId(receiver.getId());
            messageDto.setStatus(status.name());
            messageDto.setCreated(createdAt);
            return messageDto;
        }
    }
