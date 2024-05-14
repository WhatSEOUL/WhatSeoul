package com.example.whatseoul.controller;

import com.example.whatseoul.dto.PostDto;
import com.example.whatseoul.entity.Comment;
import com.example.whatseoul.service.CommentService;
import com.example.whatseoul.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @GetMapping
//    public ResponseEntity<List<PostDto>> getAllPosts() {
//        List<PostDto> posts = postService.getAllPosts();
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }

    @GetMapping
    public String getAllPosts(Model model) {
        List<PostDto> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "post/postView";
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
//        PostDto post = postService.getPostById(id);
//        return new ResponseEntity<>(post, HttpStatus.OK);
//    }
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
        return "post/postDetail"; // postDetail.html 템플릿을 렌더링
    }



//    @PostMapping
//    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {
//        PostDto createdPost = postService.createPost(postDto);
//        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
//    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("postDto", new PostDto());
        return "post/post";
    }

    @PostMapping("/create")
    public String createPost(@ModelAttribute("postDto") PostDto postDto){
        postService.createPost(postDto);
        return "redirect:/posts";
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") Long id, @RequestBody PostDto postDto) {
        PostDto updatedPost = postService.updatePost(id, postDto);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @PostMapping("/editpro")
    public String editPost(PostDto postDto) {
        PostDto updatedPost = postService.updatePost(postDto);
        return "redirect:/posts";
    }

    @GetMapping("/postedit.html")
    public String showPostEditForm() {
        return "postedit";  // 'postedit'는 실제 뷰 파일 이름과 일치해야 함
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable("id") Long id, Model model) {
        PostDto postDto = postService.getPostById(id); // 게시글 조회
        model.addAttribute("post", postDto);
        return "postedit"; // postedit.html 페이지 반환
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/delete")
    public String editPost(@RequestParam Long id) {
        postService.deletePost(id);
        return "redirect:/posts";
    }
}
