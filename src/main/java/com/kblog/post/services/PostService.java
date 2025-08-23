package com.kblog.post.services;

import java.util.Optional;

import com.kblog.post.dtos.PostResponse;
import com.kblog.post.models.Post;
import com.kblog.post.models.PostStatus;
import com.kblog.post.repositories.PostRepository;
import com.kblog.post.specs.PostSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<PostResponse> findMyPosts(String title, Long myId, Pageable amount) {

        var spec = Specification.where(PostSpecification.titleContains(title))
                .and(PostSpecification.authorIs(myId));
        var posts = repository.findAll(spec, amount);
        return posts.map(post -> toPostResponse(post));
    }

    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    public Page<PostResponse> getPostsByStatus(String title, PostStatus status, Pageable amount) {
        var spec = Specification.where(PostSpecification.titleContains(title))
                .and(PostSpecification.statusIs(status));
        var posts = repository.findAll(spec, amount);
        var responsePage = posts.map(post -> toPostResponse(post));
        return responsePage;
    }

    public Page<Post> getPostByAuthor(String title, Long myId, Pageable amount) {
        var spec = Specification.where(PostSpecification.titleContains(title))
                .and(PostSpecification.authorIs(myId));
        return repository.findAll(spec, amount);
    }

    public Post save(Post post) {

        post.setStatus(PostStatus.NEW);

        return repository.save(post);
    }

    public Post updatePostStatus(Long id, PostStatus status) {
        var post = repository.findById(id);
        return post.map(p -> {

            if (p.getStatus() != PostStatus.NEW) {
                throw new IllegalArgumentException("Post is not in NEW status");
            }
            p.setStatus(status);
            return repository.save(p);
        }).orElse(null);
    }

    private PostResponse toPostResponse(Post post) {

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getAuthor().getFullName(),
                post.getAuthor().getId(),
                post.getStatus().name(),
                post.getCreatedDate().get(),
                post.getLastModifiedDate().get(),
                post.getLastModifiedBy().get().getFullName());
    }
}
