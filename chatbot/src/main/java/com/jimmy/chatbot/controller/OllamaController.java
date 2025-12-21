package com.jimmy.chatbot.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.retry.TransientAiException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("ollama")
@RestController
public class OllamaController {

    private final ChatClient chatClient;

    public OllamaController(@Qualifier("ollamaChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public ResponseEntity<String> getChat(@RequestParam("message") String message) {
        try {
            String response = chatClient.prompt().user(message).call().content();
            return ResponseEntity.ok(response);
        } catch (TransientAiException e) {
            // Ollama-specific memory/resource errors
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("RAM full");
        } catch (Exception e) {
            // Other generic errors
            String errorMessage = e.getMessage();
            if (errorMessage != null && (errorMessage.contains("unable to allocate") || 
                                        errorMessage.contains("terminated") ||
                                        errorMessage.contains("memory"))) {
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                        .body("RAM full");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + (errorMessage != null ? errorMessage : "Unknown error"));
        }
    }
}
