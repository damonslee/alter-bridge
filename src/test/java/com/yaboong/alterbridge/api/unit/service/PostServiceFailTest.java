package com.yaboong.alterbridge.api.unit.service;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.application.api.post.service.PostServiceImpl;
import com.yaboong.alterbridge.common.TestProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by yaboong on 2019-09-16
 */
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles(TestProfile.TEST)
public class PostServiceFailTest {

    @InjectMocks
    PostServiceImpl postServiceImpl;

    @Mock
    PostRepository postRepository;

    @Test
    public void PostServiceImpl_modify_없는게시물조회_실패() {
        // GIVEN
        Long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // WHEN
        Optional<Post> postOptional = postServiceImpl.modify(postId, any());

        // THEN
        assertTrue(postOptional.isEmpty());
        verify(postRepository, never()).save(any());
    }
}
