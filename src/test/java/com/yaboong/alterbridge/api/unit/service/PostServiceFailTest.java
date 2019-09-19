package com.yaboong.alterbridge.api.unit.service;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.application.api.post.service.PostServiceImpl;
import com.yaboong.alterbridge.common.TestProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
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

    Post post;

    PostDto postDto;

    Long postId;

    @Before
    public void init() {
        post = mock(Post.class);
        postDto = mock(PostDto.class);
        postId = 1L;
    }

    @Test
    public void PostServiceImpl_modify_없는게시물조회() {
        // GIVEN
        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // WHEN
        Optional<Post> postOptional = postServiceImpl.modify(postId, postDto);

        // THEN
        assertTrue(postOptional.isEmpty());
        verify(post, never()).apply(postDto);
        verify(postRepository, never()).save(post);
    }

    @Test
    public void PostServiceImpl_softRemove_없는게시물삭제() {
        // GIVEN
        when(postServiceImpl.get(postId)).thenReturn(Optional.empty());

        // WHEN
        Optional<Post> postOptional = postServiceImpl.softRemove(postId);

        // THEN
        assertTrue(postOptional.isEmpty());
        verify(post, never()).delete();
        verify(postRepository, never()).save(post);
    }

}
