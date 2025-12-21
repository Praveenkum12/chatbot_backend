package com.jimmy.chatbot.service;

import com.jimmy.chatbot.model.ChatMemoryDetails;
import com.jimmy.chatbot.repository.ChatMemoryDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMemoryService {

    private final ChatMemoryDetailsRepository chatMemoryRepository;

    public List<ChatMemoryDetails> getChatHistoryDescending(String conversationId) {
        return chatMemoryRepository.findByConversationIdOrderByTimestampDesc(conversationId);
    }
}
