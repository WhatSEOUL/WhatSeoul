package com.example.whatseoul.controller;

import com.example.whatseoul.entity.Post;
import com.example.whatseoul.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/post/write")
    public String postWriteForm() {
        return "postwrite";
    }

    @PostMapping("/post/writepro")
    public String postWritePro(@RequestBody Post post) {

        postService.write(post);

        return "";
    }
}