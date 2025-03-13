package com.security.blogsecurity.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    private Set<Posts> posts;

    private String username;
    private String email;

    public Users() {
        posts = new HashSet<>();
    }
}
