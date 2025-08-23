package com.kblog.post;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class PostService {
    
    private final PostRepository repository;

    public PostService(PostRepository repository) {
        this.repository = repository;
    }

   
    // public Page<Post> findByAuthor(String author, Pageable amount) {
    //     return repository.findByAuthor(author, amount);
    // }

    public Optional<Post> findById(Long id) {
        return repository.findById(id);
    }

    public Post save(Post post) {

        

        return repository.save(post);
    }

}
