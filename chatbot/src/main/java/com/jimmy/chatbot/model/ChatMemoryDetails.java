package com.jimmy.chatbot.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SPRING_AI_CHAT_MEMORY")
@IdClass(ChatMemoryDetails.ChatMemoryId.class)
public class ChatMemoryDetails {

    @Id
    @Column(name = "conversation_id", nullable = false)
    private String conversationId;

    @Id
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MessageType type;   

    @PrePersist
    void prePersist() {
        if (timestamp == null) {
            timestamp = LocalDateTime.now();
        }
    }

    public enum MessageType {
        USER,
        ASSISTANT
    }

    // Composite key class
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ChatMemoryId implements Serializable {
        private String conversationId;
        private LocalDateTime timestamp;
    }
}

//----------------------------- TABLE -------------------------------------------------

//CREATE TABLE SPRING_AI_CHAT_MEMORY (
//    conversation_id VARCHAR(255) NOT NULL,
//    content TEXT NOT NULL,
//    type ENUM('USER', 'ASSISTANT') NOT NULL,
//    timestamp TIMESTAMP NOT NULL,
//    PRIMARY KEY (conversation_id, timestamp)
//);
