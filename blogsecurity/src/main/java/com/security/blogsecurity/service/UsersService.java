package com.security.blogsecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.security.blogsecurity.dto.CreateUserDto;
import com.security.blogsecurity.exception.UserAlreadyExistsException;
import com.security.blogsecurity.model.Users;
import com.security.blogsecurity.repository.UsersRepository;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public ResponseEntity<String> createUser(CreateUserDto dto) {
        if (usersRepository.findByUsername(dto.getUsername()) != null) {
            throw new UserAlreadyExistsException("Another user is using this name");
        } 
        if (usersRepository.findByEmail(dto.getEmail()) != null) {
            throw new UserAlreadyExistsException("Another user is using this email, try logging in");
            
        }
        if (dto.getUsername().isBlank()) {
            throw new NullPointerException("Username cannot be empty");
        }
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        usersRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
