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
        Users user = new Users();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        usersRepository.save(user);
        return ResponseEntity.ok().build();
    }

    public Users getUserbyUsername(String username) {
        return usersRepository.findByUsername(username);
    }
}
