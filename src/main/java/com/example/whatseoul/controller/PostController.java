package com.example.whatseoul.controller;

import com.example.whatseoul.entity.Post;
import com.example.whatseoul.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/post/write")
    public String postWriteForm() {
        return "postwrite";
    }

    @PostMapping("/post/writepro")
    public String postWritePro(@ModelAttribute Post post) {

        postService.write(post);

        return "redirect:/post/list";
    }

    @GetMapping("/post/list")
    public String postList(Model model){

        model.addAttribute("list", postService.postList());

        return "postlist";
    }

    @GetMapping("/post/edit")
    public String postEdit(Post post, Model model) {
        Post post2 = postService.findById(post.getPostId());
        model.addAttribute("post", post2);
        return "postedit";
    }

    @PostMapping("/post/editpro")
    public String postEditPro(@ModelAttribute Post post) {

        postService.edit(post);

        return "redirect:/post/list";
    }

    @GetMapping("/post/view")
    public String postview(Model model, Long id) {

        model.addAttribute("post", postService.postview(id));

        return "postview";
    }

    @GetMapping("/post/delete")
    public String postDelete(Long id) {

        postService.postDelete(id);

        return "redirect:/post/list";
    }
}
