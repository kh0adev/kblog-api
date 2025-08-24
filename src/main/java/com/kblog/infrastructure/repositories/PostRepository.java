package com.kblog.infrastructure.repositories;

import com.kblog.domain.entities.Post;
import com.kblog.domain.enums.PostStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository extends JpaRepository<Post, Long>, JpaSpecificationExecutor<Post> {

    Post findByIdAndAuthor_Id(Long id, Long authorId);

    boolean existsByIdAndAuthor_Id(Long id, Long authorId);

    Page<Post> findByAuthor_Id(Long authorId, Pageable pageable);

    Page<Post> findByStatus(PostStatus status, Pageable pageable);

}
