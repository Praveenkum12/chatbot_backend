package com.jimmy.chatbot.service;

import com.jimmy.chatbot.model.ChatDetails;
import com.jimmy.chatbot.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatDetails createNewChat(String title) {
        return chatRepository.save(ChatDetails.builder().title(title).build());
    }

    public List<ChatDetails> getChatHistory() {
        return chatRepository.findAllByOrderByCreatedAtDesc();
    }

}
