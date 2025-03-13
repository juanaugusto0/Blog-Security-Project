package com.security.blogsecurity.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.security.blogsecurity.model.Posts;
import com.security.blogsecurity.model.Users;


public interface PostsRepository extends JpaRepository<Posts, Long> {
    Optional<Posts> findByUser(Users user);
}
