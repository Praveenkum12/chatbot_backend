package com.jimmy.chatbot.service;

import com.jimmy.chatbot.model.ChatDetails;
import com.jimmy.chatbot.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public ChatDetails createNewChat(String title) {
        return chatRepository.save(ChatDetails.builder().title(title).build());
    }

}
