package com.kblog.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

    Post findByIdAndAuthor_Id(Long id, Long authorId);

    boolean existsByIdAndAuthor_Id(Long id, Long authorId);

    Page<Post> findByAuthor_Id(Long authorId, PageRequest amount);
}
