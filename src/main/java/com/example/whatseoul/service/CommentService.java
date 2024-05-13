package com.example.whatseoul.service;

import com.example.whatseoul.ResourceNotFoundException;
import com.example.whatseoul.dto.CommentDto;
import com.example.whatseoul.entity.Comment;
import com.example.whatseoul.entity.Post;
import com.example.whatseoul.entity.User;
import com.example.whatseoul.repository.post.CommentRepository;
import com.example.whatseoul.repository.post.PostRepository;
import com.example.whatseoul.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {


    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AccountService accountService;

    public Comment createComment(Long postId, String content){

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("PostId " + postId + " not found"));

        Long userId = accountService.getAuthenticatedUserId();
        User user = userRepository.findByUserId(userId);

//        String content = commentDto.getContent();
        Comment comment = Comment.builder()
                .content(content)
                .post(post)
                .user(user)
                .build();
        return commentRepository.save(comment);
    }
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findAllByPostId(postId);
    }

    @Transactional
    public CommentDto updateComment(Long commentId, String content) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        comment.setContent(content);
        commentRepository.save(comment);

        CommentDto commentDto = new CommentDto();
        return commentDto;
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}


