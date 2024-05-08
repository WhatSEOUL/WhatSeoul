package com.example.whatseoul.service;

import com.example.whatseoul.dto.CommentDto;
import com.example.whatseoul.entity.Comment;
import com.example.whatseoul.entity.Post;
import com.example.whatseoul.repository.PostRepository;
import com.example.whatseoul.respository.cityData.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public void addComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setComContent(commentDto.getComContent());
        comment.setComCreated(LocalDateTime.now());
        comment.setComModified(LocalDateTime.now());

        Post post = postRepository.findById(commentDto.getPostId()).orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);

        commentRepository.save(comment);
    }

    @Transactional
    public void editComment(CommentDto commentDto) {
        Comment comment = commentRepository.findById(commentDto.getComId())
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setComContent(commentDto.getComContent());
        comment.setComModified(LocalDateTime.now());
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long comId) {
        commentRepository.deleteById(comId);
    }

    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

}