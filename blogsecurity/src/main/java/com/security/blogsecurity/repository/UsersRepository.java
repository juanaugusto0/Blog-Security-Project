package com.security.blogsecurity.repository;

import com.security.blogsecurity.model.Users;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);
}
