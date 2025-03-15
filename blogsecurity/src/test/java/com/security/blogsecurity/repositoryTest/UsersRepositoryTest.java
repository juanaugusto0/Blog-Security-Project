package com.security.blogsecurity.repositoryTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.security.blogsecurity.model.Users;
import com.security.blogsecurity.repository.UsersRepository;

@DataJpaTest
public class UsersRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsersRepository usersRepository;

    @Test
    public void testFindUserById() {
        Users user = new Users();
        user.setUsername("testUser");
        user.setEmail("testEmail@test.com");

        entityManager.persistAndFlush(user);

        Users found = usersRepository.findById(user.getId()).orElse(null);

        assertEquals("testUser", found.getUsername());

    }

    @Test
    public void testFindUserByUsername() {
        Users user = new Users();
        user.setUsername("testUser");
        user.setEmail("testEmail@test.com");

        entityManager.persistAndFlush(user);

        Users found = usersRepository.findByUsername(user.getUsername());

        assertEquals("testUser", found.getUsername());
    }

    @Test
    public void testFindUserByEmail() {
        Users user = new Users();
        user.setUsername("testUser");
        user.setEmail("testEmail@test.com");

        entityManager.persistAndFlush(user);

        Users found = usersRepository.findByEmail(user.getEmail());

        assertEquals("testEmail@test.com", found.getEmail());
    }
}
