package com.jimmy.chatbot.controller;

import com.jimmy.chatbot.dto.ChatDTO;
import com.jimmy.chatbot.model.ChatDetails;
import com.jimmy.chatbot.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/openai")
public class OpenAIController {

    @Value("classpath:titleGenerator.st")
    Resource titleGeneratorTemplate;

    private final ChatClient casualChatClient;
    private final ChatClient webSearchChatClient;
    private final ChatClient basicChatClient;
    private final ChatService chatService;

    public OpenAIController( @Qualifier("casualChatClient") ChatClient casualChatClient, @Qualifier("webSearchChatClient") ChatClient webSearchChatClient,  @Qualifier("basicChatClient") ChatClient basicChatClient, ChatService chatService) {
        this.casualChatClient = casualChatClient;
        this.webSearchChatClient = webSearchChatClient;
        this.basicChatClient = basicChatClient;
        this.chatService = chatService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatDTO> chatWithOpenAi(@RequestBody ChatDTO chatBody) {

        String conversationId = chatBody.conversation_id();

        if (conversationId == null || conversationId.isBlank()) {
            String title = basicChatClient.prompt()
                    .system(titleGeneratorTemplate)
                    .user(chatBody.message())
                    .call()
                    .content();

            ChatDetails chatDetails = chatService.createNewChat(title);
            conversationId = chatDetails.getId();
        }

        final String finalConversationId = conversationId;

        try {
            String msg = casualChatClient.prompt()
                    .advisors(a -> a.param(CONVERSATION_ID, finalConversationId))
                    .user(chatBody.message())
                    .call()
                    .content();

            return ResponseEntity.ok(
                    ChatDTO.builder()
                            .conversation_id(finalConversationId)
                            .message(msg)
                            .build()
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(
                            ChatDTO.builder()
                                    .conversation_id(finalConversationId)
                                    .message("Error: " + e.getMessage())
                                    .build()
                    );
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
