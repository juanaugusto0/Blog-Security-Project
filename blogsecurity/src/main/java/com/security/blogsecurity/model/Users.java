package com.security.blogsecurity.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @OrderBy("timestamp ASC")
    private List<Posts> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @OrderBy("timestamp ASC")
    private List<Comments> comments;

    private String username;
    private String email;

    @ManyToMany
    @JoinTable(
        name = "user_followers",
        joinColumns = @JoinColumn(name = "follower_id"), 
        inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    private List<Users> following = new ArrayList<>();

    @ManyToMany(mappedBy = "following")
    private List<Users> followers = new ArrayList<>();

    public void follow(Users userToFollow) {
        this.following.add(userToFollow);
        userToFollow.getFollowers().add(this);
    }

    public void unfollow(Users userToUnfollow) {
        this.following.remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(this);
    }

    public Users() {
        posts = new ArrayList<>();
        comments = new ArrayList<>();
    }
}
