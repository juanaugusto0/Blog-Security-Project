package com.security.blogsecurity.serviceTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.security.blogsecurity.dto.SharePostDto;
import com.security.blogsecurity.exception.MatchingTitleException;
import com.security.blogsecurity.model.Posts;
import com.security.blogsecurity.model.Users;
import com.security.blogsecurity.repository.PostsRepository;
import com.security.blogsecurity.repository.UsersRepository;
import com.security.blogsecurity.service.PostsService;

@ExtendWith(MockitoExtension.class)
public class PostsServiceTest {

    @Mock
    private PostsRepository postsRepository;
    @Mock
    private UsersRepository usersRepository;
    @InjectMocks
    private PostsService postsService;

    @Test
    public void testGetAllPosts_Success() {
        Users mockUser = new Users();
        mockUser.setUsername("testUser");
        mockUser.setEmail("testemail@gmail.com");
        mockUser.setId(1L);

        Posts mockPost = new Posts();
        mockPost.setId(1L);
        mockPost.setTitle("testTitle");
        mockPost.setContent("testContent");
        mockPost.setUser(mockUser);
        mockPost.setTimestamp(new java.sql.Timestamp(System.currentTimeMillis()));

        when(postsRepository.findAll()).thenReturn(Arrays.asList(mockPost));

        List<Posts> posts = postsService.getAllPosts();

        assertEquals(1, posts.size());
        assertEquals(1L, posts.get(0).getId());
        assertEquals(1L, posts.get(0).getUser().getId());

    }

    @Test
    public void getAllPosts_Success_NoPosts() {
        when(postsRepository.findAll()).thenReturn(Arrays.asList());
        List<Posts> posts = postsService.getAllPosts();
        assertEquals(0, posts.size());
    }

    @Test
    public void sharePost_Success() {
        Users mockUser = new Users();
        mockUser.setUsername("testUser");
        mockUser.setEmail("testemail@gmail.com");
        mockUser.setId(1L);

        SharePostDto dto = new SharePostDto();
        dto.setTitleOfPost("testTitle");
        dto.setContentOfPost("testContent");
        dto.setUsername("testUser");

        when(usersRepository.findByUsername("testUser")).thenReturn(mockUser);
       
        Posts expectedPost = new Posts();
        expectedPost.setTitle("testTitle");
        expectedPost.setContent("testContent");
        expectedPost.setUser(mockUser);

        when(postsRepository.save(any(Posts.class))).thenReturn(expectedPost);

        ResponseEntity<?> response = postsService.sharePost(dto);


        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(postsRepository, times(1)).save(any(Posts.class));
    }

    @Test
    public void sharePost_Failure_MatchingTitle() {
        Users mockUser = new Users();
        mockUser.setUsername("testUser");
        mockUser.setEmail("testemail@gmail.com");
        mockUser.setId(1L);

        SharePostDto dto = new SharePostDto();
        dto.setTitleOfPost("testTitle");
        dto.setContentOfPost("testContent");
        dto.setUsername("testUser");


        Posts matchingTitlePost = new Posts();
        matchingTitlePost.setTitle("testTitle");
        matchingTitlePost.setContent("anyContent");
        matchingTitlePost.setUser(mockUser);

        when(postsRepository.findAll()).thenReturn(Arrays.asList(matchingTitlePost));

        assertThrows(MatchingTitleException.class, ()->{postsService.sharePost(dto);});
    }

    @Test
    public void sharePost_Failure_EmptyTitle() {
        SharePostDto dto = new SharePostDto();
        dto.setTitleOfPost("");
        dto.setContentOfPost("testContent");
        dto.setUsername("testUser");

        assertThrows(NullPointerException.class, ()->{postsService.sharePost(dto);});
    }

    @Test
    public void sharePost_Failure_EmptyContent() {
        SharePostDto dto = new SharePostDto();
        dto.setTitleOfPost("testTitle");
        dto.setContentOfPost("");
        dto.setUsername("testUser");

        assertThrows(NullPointerException.class, ()->{postsService.sharePost(dto);});
    }

}
