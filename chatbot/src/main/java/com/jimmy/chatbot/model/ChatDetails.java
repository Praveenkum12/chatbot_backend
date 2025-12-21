package com.jimmy.chatbot.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "chat_details")
public class ChatDetails {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    void generateId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}

//----------------------------- TABLE -------------------------------------------------

//CREATE TABLE chat_details (
//        id CHAR(36) PRIMARY KEY,
//        title VARCHAR(255) NOT NULL,
//        created_at DATETIME NOT NULL
//);


