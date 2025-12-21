package com.jimmy.chatbot.repository;

import com.jimmy.chatbot.model.ChatDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRepository extends JpaRepository<ChatDetails, String> {
}
