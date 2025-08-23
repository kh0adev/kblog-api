package com.kblog.post.dtos;

import java.time.LocalDateTime;

public record PostResponse(Long id, String title, String content, String author, String status, LocalDateTime createdAt,
        LocalDateTime updatedAt, String lastModifiedBy) {

}
