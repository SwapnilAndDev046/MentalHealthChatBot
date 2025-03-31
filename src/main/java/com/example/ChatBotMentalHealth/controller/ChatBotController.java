package com.example.ChatBotMentalHealth.controller;

import com.example.ChatBotMentalHealth.model.ChatRequest;
import com.example.ChatBotMentalHealth.model.ChatResponse;
import com.example.ChatBotMentalHealth.service.ChatBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatBotController {

    private final ChatBotService chatBotService;

    @Autowired
    public ChatBotController(ChatBotService chatBotService) {
        this.chatBotService = chatBotService;
    }

    /**
     * Example usage:
     * POST /chat/message?userId=123
     * Body: {"message": "Hello"}
     */
    @PostMapping("/message")
    public ChatResponse getResponse(
            @RequestParam(required = false, defaultValue = "guest") String userId,
            @RequestBody ChatRequest request
    ) {
        // 1. Extract user message from the request
        String userMessage = request.getMessage();

        // 2. Delegate to the service for a reply, passing in userId
        String botReply = chatBotService.getBotReply(userId, userMessage);

        // 3. Build a response object
        ChatResponse response = new ChatResponse();
        response.setReply(botReply);

        return response;
    }
}
