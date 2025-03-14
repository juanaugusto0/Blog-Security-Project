package com.security.blogsecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.blogsecurity.dto.SharePostDto;
import com.security.blogsecurity.model.Posts;
import com.security.blogsecurity.service.PostsService;

@RestController
public class PostsController {

    @Autowired
    private PostsService postsService;
    
    @GetMapping("/posts")
    public List<Posts> getAllPosts() {
        return postsService.getAllPosts();
    }

    @PostMapping("/share")
    public ResponseEntity<String> sharePost(@RequestBody SharePostDto dto) {
        return postsService.sharePost(dto);
    }
}
