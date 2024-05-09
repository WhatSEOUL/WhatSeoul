package com.example.whatseoul.controller;

import com.example.whatseoul.dto.CommentDto;
import com.example.whatseoul.dto.PostDto;
import com.example.whatseoul.service.CommentService;
import com.example.whatseoul.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long postId, @RequestBody CommentDto commentDto) {
        PostDto post = postService.getPostById(postId);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        commentDto.setPostId(postId);
        CommentDto createdComment = commentService.createComment(commentDto);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @PutMapping("/comments/{comId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long comId, @RequestBody CommentDto updatedCommentDto) {
        CommentDto updatedComment = commentService.updateComment(comId, updatedCommentDto);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{comId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long comId) {
        commentService.deleteComment(comId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}