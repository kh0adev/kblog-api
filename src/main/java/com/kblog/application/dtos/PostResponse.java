package com.kblog.application.dtos;

import java.time.LocalDateTime;

public record PostResponse(Long id, String title, String content, String author,Long authorId, String status, LocalDateTime createdAt,
        LocalDateTime updatedAt, String lastModifiedBy) {

}
