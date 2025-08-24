package com.kblog.api.controllers;

import java.net.URI;

import com.kblog.application.dtos.PostRequest;
import com.kblog.application.dtos.PostResponse;
import com.kblog.application.services.PostService;
import com.kblog.domain.entities.Post;
import com.kblog.domain.entities.User;
import com.kblog.domain.enums.PostStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping("/posts")
    private ResponseEntity<Void> createPosts(@RequestBody @Valid PostRequest request,
            UriComponentsBuilder ucb, @AuthenticationPrincipal User user) {

        var post = Post.builder()
                .title(request.title())
                .content(request.content())
                .author(user).build();

        var savedPost = service.save(post);

        URI locationOfNewPost = ucb
                .path("post/{id}")
                .buildAndExpand(savedPost.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewPost).build();
    }

    @GetMapping("/{requestedId}")
    public ResponseEntity<Post> findById(@PathVariable Long requestedId) {
        var post = service.findById(requestedId);
        return post.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostResponse>> getPosts(@RequestParam(required = false) String title,
            Pageable pageable) {
        var posts = service.getPostsByStatus(title, PostStatus.APPROVED, pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/own")
    public ResponseEntity<Page<PostResponse>> getMyPosts(@RequestParam(required = false) String title,
            Pageable pageable, @AuthenticationPrincipal User user) {
        var posts = service.findMyPosts(title, user.getId(), pageable);
        return ResponseEntity.ok(posts);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/posts/pending")
    public ResponseEntity<Page<PostResponse>> getPendingPosts(@RequestParam(required = false) String title,
            Pageable pageable) {
        var posts = service.getPostsByStatus(title, PostStatus.NEW, pageable);
        return ResponseEntity.ok(posts);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/posts/{id}/approve")
    public ResponseEntity<?> approvePost(@PathVariable Long id) {
        var post = service.updatePostStatus(id, PostStatus.APPROVED);

        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/posts/{id}/reject")
    public ResponseEntity<?> rejectPost(@PathVariable Long id) {
        var post = service.updatePostStatus(id, PostStatus.REJECTED);
        if (post == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

}
