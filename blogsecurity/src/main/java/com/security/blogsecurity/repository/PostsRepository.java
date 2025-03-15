package com.security.blogsecurity.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.security.blogsecurity.model.Posts;
import com.security.blogsecurity.model.Users;


public interface PostsRepository extends JpaRepository<Posts, Long> {

    @Query("SELECT p FROM Posts p WHERE p.user = :user ORDER BY p.timestamp ASC")
    List<Posts> findByUser(@Param("user") Users user);
}
