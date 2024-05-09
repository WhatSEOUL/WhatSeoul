//package com.example.whatseoul.controller;
//
//import com.example.whatseoul.dto.CommentDto;
//import com.example.whatseoul.service.CommentService;
//import com.example.whatseoul.service.PostService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
////@Controller
//@RequiredArgsConstructor
//@RequestMapping("/posts/comments")
//public class CommentController {
//
//    private final CommentService commentService;
//    private final PostService postService;
//
//    @GetMapping
//    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {
//        List<CommentDto> comments = commentService.getCommentsByPostId(postId);
//        return new ResponseEntity<>(comments, HttpStatus.OK);
//    }
//
//    @PostMapping
//    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto) {
//        CommentDto createdComment = commentService.createComment(commentDto);
//        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
//    }
//
//    @PutMapping
//    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId, @RequestBody CommentDto updatedCommentDto) {
//        CommentDto updatedComment = commentService.updateComment(commentId, updatedCommentDto);
//        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
//    }
//
//    @DeleteMapping
//    public ResponseEntity<?> deleteComment(@PathVariable Long commentId) {
//        commentService.deleteComment(commentId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//
//}