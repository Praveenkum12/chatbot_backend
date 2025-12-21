package com.jimmy.chatbot.dto;

import lombok.Builder;

@Builder
public record ChatDTO(String message, String conversation_id) {
}
