package com.security.blogsecurity.controllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.blogsecurity.controller.UsersController;
import com.security.blogsecurity.dto.CreateUserDto;
import com.security.blogsecurity.service.UsersService;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UsersService usersService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void testCreateUser() throws Exception {
        CreateUserDto dto = new CreateUserDto();
        dto.setUsername("testUser");
        dto.setEmail("testEmail@test.com");

        when(usersService.createUser(dto)).thenReturn(ResponseEntity.ok("User Created Successfully"));

        mockMvc.perform(post("/create")
            .with(csrf()) // Adiciona o token CSRF
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk());
    }
}
