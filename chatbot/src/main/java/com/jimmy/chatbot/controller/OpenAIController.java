package com.jimmy.chatbot.controller;

import com.jimmy.chatbot.dto.ChatDTO;
import org.springframework.ai.chat.client.ChatClient;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/openai")
public class OpenAIController {

    private final ChatClient casualChatClient;
    private final ChatClient webSearchChatClient;

    public OpenAIController( @Qualifier("casualChatClient") ChatClient casualChatClient, @Qualifier("webSearchChatClient") ChatClient webSearchChatClient) {
        this.casualChatClient = casualChatClient;
        this.webSearchChatClient = webSearchChatClient;
    }

    @PostMapping("/chat")
    public ResponseEntity<String> chatWithOpenAi(@RequestBody ChatDTO chatBody) {
        try {
            String response = casualChatClient.prompt()
                    .advisors(a -> a.param(CONVERSATION_ID, "jimmy"))
                    .user(chatBody.message()).call().content();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/web-search")
    public ResponseEntity<String> searchOnWeb(@RequestBody ChatDTO chatBody) {
        try {
            String response = webSearchChatClient.prompt().user(chatBody.message()).call().content();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

}
