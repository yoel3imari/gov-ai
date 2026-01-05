package com.xozev.morgovai.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ChatRequest {

    private String title;

    @NotNull(message = "Service ID cannot be null")
    private UUID serviceId;
}
