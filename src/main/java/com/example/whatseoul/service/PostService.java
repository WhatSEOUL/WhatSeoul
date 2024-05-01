package com.example.whatseoul.service;

import com.example.whatseoul.entity.Post;
import com.example.whatseoul.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Transactional
    public void write(Post post) {

        postRepository.save(post);

    }
}