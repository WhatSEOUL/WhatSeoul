package com.example.whatseoul.service;

import com.example.whatseoul.entity.Post;
import com.example.whatseoul.repository.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    //게시글 생성
    @Transactional
    public void write(Post post) {

        postRepository.save(post);

    }

    //게시글 리스트 처리
    public List<Post>postList() {
        return postRepository.findAll();
    }


    public Post findById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    //게시글 수정
    public void edit(Post post) {
        Post post2 = postRepository.findById(post.getPostId()).orElse(null);
        post2.setPostTitle(post.getPostTitle());
        post2.setPostContent(post.getPostContent());
        postRepository.save(post2);
    }

    //특정 게시글 불러오기
    public Post postview(Long id) {

        return postRepository.findById(id).get();
    }

    //특정 게시글 삭제

    public void postDelete(Long id) {

        postRepository.deleteById(id);
    }

}