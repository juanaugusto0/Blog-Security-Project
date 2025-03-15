package com.security.blogsecurity.controllerTest;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.blogsecurity.controller.PostsController;
import com.security.blogsecurity.dto.SharePostDto;
import com.security.blogsecurity.model.Posts;
import com.security.blogsecurity.service.PostsService;

@WebMvcTest(PostsController.class)
public class PostsControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PostsService postsService;

    @Test
    @WithMockUser
    public void testGetAllPosts() throws Exception {
        Posts mockPost = new Posts();
        mockPost.setTitle("testTitle");
        mockPost.setContent("testContent");
        mockPost.setId(1L);

        when(postsService.getAllPosts()).thenReturn(List.of(mockPost));

        mockMvc.perform(get("/posts")) // Agora o get() será reconhecido
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].title").value("testTitle"))
            .andExpect(jsonPath("$[0].content").value("testContent"));
        
        }

    @Test
    @WithMockUser
    public void testGetAllPosts_Empty() throws Exception {
        when(postsService.getAllPosts()).thenReturn(List.of());

        mockMvc.perform(get("/posts"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    @WithMockUser
    public void testSharePost() throws Exception {
        SharePostDto dto = new SharePostDto();
        dto.setTitleOfPost("testTitle");
        dto.setContentOfPost("testContent");
        dto.setUsername("testUser");

        when(postsService.sharePost(dto)).thenReturn(ResponseEntity.ok("Post Shared Successfully"));

        mockMvc.perform(post("/share")
            .with(csrf()) // Adiciona o token CSRF
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(dto)))
        .andExpect(status().isOk());
    }
}
