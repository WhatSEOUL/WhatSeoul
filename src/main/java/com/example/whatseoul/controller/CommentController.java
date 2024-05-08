package com.example.whatseoul.controller;

import com.example.whatseoul.dto.CommentDto;
import com.example.whatseoul.service.CommentService;
import com.example.whatseoul.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;

    @GetMapping("/post/view")
    public String viewPost(@RequestParam Long postId, Model model) {
        model.addAttribute("post", postService.findById(postId));
        model.addAttribute("comments", commentService.getCommentsByPostId(postId));
        return "postview";
    }

    @PostMapping("/post/comment")
    public String addComment(@ModelAttribute CommentDto commentDto) {
        commentService.addComment(commentDto);
        return "redirect:/post/view?postId=" + commentDto.getPostId();
    }

    @PostMapping("/post/comment/edit")
    public String editComment(@ModelAttribute CommentDto commentDto) {
        commentService.editComment(commentDto);
        return "redirect:/post/view?postId=" + commentDto.getPostId();
    }

    @PostMapping("/post/comment/delete")
    public String deleteComment(@RequestParam Long comId, @RequestParam Long postId) {
        commentService.deleteComment(comId);
        return "redirect:/post/view?postId=" + postId;
    }

}