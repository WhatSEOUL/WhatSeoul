package com.example.whatseoul.controller;

import com.example.whatseoul.dto.PostDto;
import com.example.whatseoul.entity.Comment;
import com.example.whatseoul.service.CommentService;
import com.example.whatseoul.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private  final CommentService commentService;


    @GetMapping
    public String getAllPosts(Model model) {
        List<PostDto> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "./post/postView";
    }

    @GetMapping("/{id}")
    public String getPostById(@PathVariable("id") Long id, Model model) {
        PostDto post = postService.getPostById(id);
        model.addAttribute("post", post);
      
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        model.addAttribute("currentUser", username);
        List<Comment> comments = commentService.getCommentsByPostId(post.getId());
        model.addAttribute("comments", comments);

        return "./post/postDetail";
    }


    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("postDto", new PostDto());
        return "./post/post";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("postDto") PostDto postDto){
        postService.createPost(postDto);
        return "redirect:/posts";
    }

    @PostMapping("/editpro")
    public String editPost(PostDto postDto) {
        postService.updatePost(postDto);
        return "redirect:/posts";
    }

    @GetMapping("/postedit.html")
    public String showPostEditForm() {
        return "postedit";
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable("id") Long id, Model model) {
        PostDto postDto = postService.getPostById(id); // 게시글 조회
        model.addAttribute("post", postDto);
        return "postedit";
    }


    @GetMapping("/delete")
    public String editPost(@RequestParam Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
