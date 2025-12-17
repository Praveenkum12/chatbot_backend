package com.jimmy.chatbot.controller;

import com.jimmy.chatbot.dto.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OpenAIController {

    private final ChatClient chatClient;

    @PostMapping("/chat")
    public String chatWithOpenAi(@RequestBody ChatDTO chatBody) {
        return chatClient.prompt().user(chatBody.message()).call().content();
    }

}
