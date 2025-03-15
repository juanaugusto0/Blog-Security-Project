package com.security.blogsecurity.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.security.blogsecurity.model.Posts;
import com.security.blogsecurity.model.Users;
import com.security.blogsecurity.repository.PostsRepository;

@DataJpaTest
public class PostsRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PostsRepository postsRepository;

    @Test
    public void testFindPostById() {
        Posts post = new Posts();
        post.setTitle("testTitle");
        post.setContent("testContent");

        entityManager.persistAndFlush(post);

        Posts found = postsRepository.findById(post.getId()).orElse(null);

        assertEquals("testTitle", found.getTitle());
    }

    @Test
    public void testFindByUsers() {
        Users user = new Users();
        user.setUsername("testUser");
        user.setEmail("testEmail@test.com");
        
        Posts post = new Posts();
        post.setTitle("testTitle");
        post.setContent("testContent");
        post.setUser(user);
        user.getPosts().add(post);

        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(post);

        List<Posts> found = postsRepository.findByUser(user);

        assertEquals("testTitle", found.get(0).getTitle());
    }

    @Test
    public void testFindByUsers_Empty() {
        Users user = new Users();
        user.setUsername("testUser");
        user.setEmail("testEmail@test.com");

        entityManager.persistAndFlush(user);

        List<Posts> emptyList = postsRepository.findByUser(user);

        assertEquals(0, emptyList.size());
    }

    @Test
    public void testFindByUsers_MultiplePosts() {
        Users user = new Users();
        user.setUsername("testUser");
        user.setEmail("testEmail@test.com");

        Posts post1 = new Posts();
        post1.setTitle("testTitle1");
        post1.setContent("testContent1");
        post1.setUser(user);
        post1.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        user.getPosts().add(post1);

        Posts post2 = new Posts();
        post2.setTitle("testTitle2");
        post2.setContent("testContent2");
        post2.setUser(user);
        post2.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));
        user.getPosts().add(post2);

        entityManager.persistAndFlush(user);
        entityManager.persistAndFlush(post1);
        entityManager.persistAndFlush(post2);

        List<Posts> found = postsRepository.findByUser(user);

        assertEquals(2, found.size());
        assertEquals("testTitle1", found.get(0).getTitle());
        assertEquals("testTitle2", found.get(1).getTitle());
    }
}
