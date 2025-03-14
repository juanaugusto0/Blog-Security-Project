package com.security.blogsecurity.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.blogsecurity.model.Comments;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
}
