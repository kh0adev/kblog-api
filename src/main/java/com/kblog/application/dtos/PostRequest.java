package com.kblog.application.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PostRequest(
        @NotBlank(message = "Title is required") String title,
        @Size(min = 10, message = "Content must be at least 10 characters") String content) {
}