package com.security.blogsecurity.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.security.blogsecurity.dto.CreateUserDto;
import com.security.blogsecurity.exception.UserAlreadyExistsException;
import com.security.blogsecurity.model.Users;
import com.security.blogsecurity.repository.UsersRepository;
import com.security.blogsecurity.service.UsersService;

@ExtendWith(MockitoExtension.class)
public class UsersServiceTest {

    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private UsersService usersService;

    @Test
    public void testCreateUser_Success() {
        CreateUserDto mockDto = new CreateUserDto();
        mockDto.setUsername("testUser");
        mockDto.setEmail("testemail@gmail.com");

        Users expectedUser = new Users();
        expectedUser.setUsername("testUser");
        expectedUser.setEmail("testemail@gmail.com");

        when(usersRepository.findByUsername("testUser")).thenReturn(null);
        when(usersRepository.save(any(Users.class))).thenReturn(expectedUser);

        ResponseEntity<?> response = usersService.createUser(mockDto);

        assertEquals(ResponseEntity.ok("User Created Successfully"), response);

        verify(usersRepository, times(1)).save(any(Users.class));
    }

    @Test
    public void testCreateUser_Failure_UserAlreadyExists() {
        CreateUserDto mockDto = new CreateUserDto();
        mockDto.setUsername("testUser");
        mockDto.setEmail("testemail");

        Users repeatedUser = new Users();
        repeatedUser.setUsername("testUser");
        repeatedUser.setEmail("testemail2");

        when(usersRepository.findByUsername("testUser")).thenReturn(repeatedUser);

        assertThrows(UserAlreadyExistsException.class, () -> {
            usersService.createUser(mockDto);
        });
    }

    @Test
    public void testCreateUser_Failure_EmailAlreadyExists() {
        CreateUserDto mockDto = new CreateUserDto();
        mockDto.setUsername("testUser");
        mockDto.setEmail("testemail");

        Users repeatedUser = new Users();
        repeatedUser.setUsername("testUser2");
        repeatedUser.setEmail("testemail");

        when(usersRepository.findByUsername("testUser")).thenReturn(null);
        when(usersRepository.findByEmail("testemail")).thenReturn(repeatedUser);

        assertThrows(UserAlreadyExistsException.class, () -> {
            usersService.createUser(mockDto);
        });

    }

    @Test
    public void testCreateUser_Failure_EmptyUsername() {
        CreateUserDto mockDto = new CreateUserDto();
        mockDto.setUsername("");
        mockDto.setEmail("testemail");

        assertThrows(NullPointerException.class, () -> {
            usersService.createUser(mockDto);
        });
    }
}
