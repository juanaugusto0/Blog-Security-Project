package com.security.blogsecurity.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserDto {
    private String username;
    private String email;
}
