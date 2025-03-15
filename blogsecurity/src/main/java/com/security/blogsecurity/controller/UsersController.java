package com.security.blogsecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.security.blogsecurity.dto.CreateUserDto;
import com.security.blogsecurity.service.UsersService;

@RestController
public class UsersController {
    @Autowired
    private UsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody CreateUserDto dto) {
        return usersService.createUser(dto);
    }
}
