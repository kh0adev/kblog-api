package com.kblog.infrastructure.specs;

import com.kblog.domain.entities.Post;
import com.kblog.domain.enums.PostStatus;

import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {
    public static Specification<Post> titleContains(String title) {
        return (root, query, cb) -> {
            if (title == null || title.isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(root.get("title"), "%" + title + "%");
        };
    }

    public static Specification<Post> authorIs(Long authorId) {
        return (root, query, cb) -> cb.equal(root.get("author").get("id"), authorId);
    }

    public static Specification<Post> statusIs(PostStatus status) {
        return (root, query, cb) -> cb.equal(root.get("status"), status);
    }
}
