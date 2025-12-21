package com.jimmy.chatbot.model;

import jakarta.persistence.*;

import java.util.UUID;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name = "chat_details")
public class ChatDetails {

    @Id
    @Column(length = 36, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String title;

    @PrePersist
    void generateId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}
//----------------------------- TABLE -------------------------------------------------

//CREATE TABLE chat_details (
//        id CHAR(36) PRIMARY KEY,
//title VARCHAR(255) NOT NULL
//);


