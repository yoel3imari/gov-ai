package com.xozev.morgovai.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class GovServiceDTO {

    private UUID id;

    @NotBlank(message = "Name cannot be blank")
    private String name;
}
