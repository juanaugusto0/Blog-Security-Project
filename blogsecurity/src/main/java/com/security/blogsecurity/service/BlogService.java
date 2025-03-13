package com.security.blogsecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.security.blogsecurity.dto.CreateUserDto;
import com.security.blogsecurity.dto.SharePostDto;
import com.security.blogsecurity.exception.MatchingTitleException;
import com.security.blogsecurity.exception.UserAlreadyExistsException;
import com.security.blogsecurity.model.Posts;
import com.security.blogsecurity.model.Users;
import com.security.blogsecurity.repository.PostsRepository;
import com.security.blogsecurity.repository.UsersRepository;

@Service
public class BlogService {
    @Autowired
    private PostsRepository postsRepository;
    
    @Autowired
    private UsersRepository usersRepository;

    private void setTimestamp(Posts post) {
        post.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
    }

    public List<Posts> getAllPosts() {
        return postsRepository.findAll();
    }

    public ResponseEntity<String> sharePost(SharePostDto dto) {
        for (Posts posts : getAllPosts()) {
            if (dto.getTitleOfPost().equals(posts.getTitle())) {
                throw new MatchingTitleException("Title "+dto.getTitleOfPost()+" already exists");
            }
        }

        Posts post = new Posts();
        post.setTitle(dto.getTitleOfPost());
        post.setContent(dto.getContentOfPost());
        post.setUser(usersRepository.findByUsername(dto.getUsername()));
        setTimestamp(post);
        postsRepository.save(post);
        return ResponseEntity.ok().build() ;
    }

    public ResponseEntity<String> createUser(CreateUserDto dto) {
        if (usersRepository.findByUsername(dto.getUsername()) != null) {
            throw new UserAlreadyExistsException("Another user is using this name");
        }
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        usersRepository.save(user);
        return ResponseEntity.ok().build();
    }


}
