package com.kblog.post.services;

import java.util.Optional;

import com.kblog.post.dtos.PostResponse;
import com.kblog.post.models.Post;
import com.kblog.post.models.PostStatus;
import com.kblog.post.repositories.PostRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

    public Page<Post> findAll(Pageable amount) {
        return repository.findAll(amount);
    }

    public Page<Post> findMyPosts(Long myId, Pageable amount) {
        return repository.findByAuthor_Id(myId, amount);
    }

    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    public Page<PostResponse> getPostsByStatus(PostStatus status, Pageable amount) {
        var posts = repository.findByStatus(status, amount);
        var responsePage = posts.map(post -> new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getFullName(),
                post.getStatus().name(),
                post.getCreatedDate().get(),
                post.getLastModifiedDate().get(),
                post.getLastModifiedBy().get().getFullName()));
        return responsePage;
    }

    public Page<Post> getPostByAuthor(Long myId, Pageable amount) {
        return repository.findByAuthor_Id(myId, amount);
    }

    public Post save(Post post) {

        post.setStatus(PostStatus.NEW);

        return repository.save(post);
    }

}
