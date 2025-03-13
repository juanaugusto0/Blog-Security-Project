package com.security.blogsecurity.model;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    
    private String title;

    private String content;

    private Timestamp timestamp;
}
