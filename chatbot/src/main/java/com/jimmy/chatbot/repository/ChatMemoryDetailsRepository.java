package com.jimmy.chatbot.repository;

import com.jimmy.chatbot.model.ChatMemoryDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMemoryDetailsRepository extends JpaRepository<ChatMemoryDetails, ChatMemoryDetails.ChatMemoryId> {

    List<ChatMemoryDetails> findByConversationIdOrderByTimestampAsc(String conversationId);

    List<ChatMemoryDetails> findByConversationIdOrderByTimestampDesc(String conversationId);
}
