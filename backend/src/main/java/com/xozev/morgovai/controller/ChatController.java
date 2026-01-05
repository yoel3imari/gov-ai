package com.xozev.morgovai.controller;

import com.xozev.morgovai.dto.ChatRequest;
import com.xozev.morgovai.dto.FeedbackRequest;
import com.xozev.morgovai.dto.MessageRequest;
import com.xozev.morgovai.entity.Chat;
import com.xozev.morgovai.entity.LlmResponse;
import com.xozev.morgovai.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/start")
    public ResponseEntity<Chat> startChat(@Valid @RequestBody ChatRequest request) {
        return ResponseEntity.ok(chatService.createChat(request));
    }

    @PostMapping("/message")
    public ResponseEntity<LlmResponse> sendMessage(@Valid @RequestBody MessageRequest request) {
        return ResponseEntity.ok(chatService.sendMessage(request));
    }

    @PostMapping("/feedback")
    public ResponseEntity<LlmResponse> submitFeedback(@Valid @RequestBody FeedbackRequest request) {
        return ResponseEntity.ok(chatService.submitFeedback(request));
    }
}
