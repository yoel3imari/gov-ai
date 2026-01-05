package com.xozev.morgovai.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class DocDTO {

    private UUID id;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    @NotBlank(message = "Path cannot be blank")
    private String path;

    private Long size;

    private String lang;

    @NotNull(message = "Service ID cannot be null")
    private UUID serviceId;
}
