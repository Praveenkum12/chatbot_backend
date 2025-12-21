package com.jimmy.chatbot.config;

import com.jimmy.chatbot.webflow.WebSearchDocumentRetriever;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import java.util.List;

@Configuration
public class OpenAIConfig {


    @Bean
    ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return MessageWindowChatMemory.builder().maxMessages(50)
                .chatMemoryRepository(jdbcChatMemoryRepository).build();
    }

    @Bean("casualChatClient")
    public ChatClient openaiChatClient( ChatMemory chatMemory, OpenAiChatModel chatModel) {
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        return ChatClient.builder(chatModel)
                .defaultAdvisors(List.of(
                        memoryAdvisor))
                .build();
    }

    @Bean("basicChatClient")
    public ChatClient basicChatClient( ChatMemory chatMemory, OpenAiChatModel chatModel) {
        return ChatClient.builder(chatModel)
                .build();
    }

    @Bean("webSearchChatClient")
    public ChatClient webSearchChatClient(OpenAiChatModel chatModel,
                                 ChatMemory chatMemory, RestClient.Builder restClientBuilder) {
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        var webSearchRAGAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(WebSearchDocumentRetriever.builder()
                        .restClientBuilder(restClientBuilder).maxResults(5).build())
                .build();
        return ChatClient.builder(chatModel)
                .defaultAdvisors(List.of(
                        memoryAdvisor,
                        webSearchRAGAdvisor))
                .build();
    }

}
