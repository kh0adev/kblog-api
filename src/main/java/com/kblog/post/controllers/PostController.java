package com.kblog.post.controllers;

import java.net.URI;

import com.kblog.post.dtos.PostRequest;
import com.kblog.post.dtos.PostResponse;
import com.kblog.post.models.Post;
import com.kblog.post.models.PostStatus;
import com.kblog.post.services.PostService;
import com.kblog.user.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
    private ResponseEntity<Void> createPosts(@RequestBody PostRequest request,
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
    public ResponseEntity<Page<Post>> getPosts(Pageable pageable) {
        var posts = service.findAll(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/posts/own")
    public ResponseEntity<Page<Post>> getMyPosts(Pageable pageable, @AuthenticationPrincipal User user) {
        var posts = service.findMyPosts(user.getId(), pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/admin/posts/pending")
    public ResponseEntity<Page<PostResponse>> getPendingPosts(Pageable pageable) {
        var posts = service.getPostsByStatus(PostStatus.NEW, pageable);
        return ResponseEntity.ok(posts);
    }

}
