package com.kblog.post;

import java.net.URI;
import com.kblog.user.User;

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
@RequestMapping("/post")
public class PostController {


    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @PostMapping
    private ResponseEntity<Void> createCashCard(@RequestBody PostRequest request,
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
}
