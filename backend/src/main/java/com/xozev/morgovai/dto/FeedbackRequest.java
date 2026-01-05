package com.xozev.morgovai.dto;

import com.xozev.morgovai.entity.Feedback;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class FeedbackRequest {

    @NotNull(message = "LLM Response ID cannot be null")
    private UUID llmResponseId;

    @NotNull(message = "Feedback cannot be null")
    private Feedback feedback;
}
