package com.xozev.morgovai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class MessageRequest {

    @NotNull(message = "Chat ID cannot be null")
    private UUID chatId;

    @NotBlank(message = "Text cannot be blank")
    private String text;
}
