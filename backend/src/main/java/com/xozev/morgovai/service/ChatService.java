package com.xozev.morgovai.service;

import com.xozev.morgovai.dto.ChatRequest;
import com.xozev.morgovai.dto.FeedbackRequest;
import com.xozev.morgovai.dto.MessageRequest;
import com.xozev.morgovai.entity.*;
import com.xozev.morgovai.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final GovServiceRepository govServiceRepository;
    private final UserMessageRepository userMessageRepository;
    private final LlmResponseRepository llmResponseRepository;
    private final RagService ragService;

    public Chat createChat(ChatRequest request) {
        GovService service = govServiceRepository.findById(request.getServiceId())
                .orElseThrow(() -> new EntityNotFoundException("GovService not found"));
        
        Chat chat = new Chat();
        chat.setTitle(request.getTitle() != null ? request.getTitle() : "New Chat");
        chat.setService(service);
        return chatRepository.save(chat);
    }

    public LlmResponse sendMessage(MessageRequest request) {
        Chat chat = chatRepository.findById(request.getChatId())
                .orElseThrow(() -> new EntityNotFoundException("Chat not found"));

        // Save User Message
        UserMessage userMessage = new UserMessage();
        userMessage.setChat(chat);
        userMessage.setText(request.getText());
        userMessageRepository.save(userMessage);

        // Retrieve Answer via RAG
        String responseText = ragService.generateResponse(request.getText(), chat);

        // Save LLM Response
        LlmResponse llmResponse = new LlmResponse();
        llmResponse.setChat(chat);
        llmResponse.setText(responseText);
        llmResponse.setFeedback(Feedback.NONE);
        
        return llmResponseRepository.save(llmResponse);
    }

    public LlmResponse submitFeedback(FeedbackRequest request) {
        LlmResponse llmResponse = llmResponseRepository.findById(request.getLlmResponseId())
                .orElseThrow(() -> new EntityNotFoundException("LLM Response not found"));
        
        llmResponse.setFeedback(request.getFeedback());
        return llmResponseRepository.save(llmResponse);
    }
}
