package com.jimmy.chatbot.controller;

import com.jimmy.chatbot.dto.ChatDTO;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/openai")
public class OpenAIController {


    private final ChatClient chatClient;

    public OpenAIController( @Qualifier("openaiChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chatWithOpenAi(@RequestBody ChatDTO chatBody) {
        try {
            String response = chatClient.prompt().user(chatBody.message()).call().content();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error: " + e.getMessage());
        }
    }

}
