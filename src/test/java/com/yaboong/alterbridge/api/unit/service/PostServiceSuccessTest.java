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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by yaboong on 2019-09-16
 */
@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles(TestProfile.TEST)
public class PostServiceSuccessTest {

    @InjectMocks
    PostServiceImpl postServiceImpl;

    @Mock
    PostRepository postRepository;

    Post post;

    PostDto postDto;

    @Before
    public void init() {
        post = mock(Post.class);
        postDto = mock(PostDto.class);
    }


    @Test
    public void Service_게시물_생성_성공() {
        // WHEN
        postServiceImpl.create(postDto);

        // THEN
        verify(postRepository, times(1)).save(any());
    }

    @Test
    public void Service_게시물_수정_성공() {
        // GIVEN
        long postId = 1L;
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(post.apply(postDto)).thenReturn(post);

        // WHEN
        postServiceImpl.modify(postId, postDto);

        // THEN
        verify(postRepository, times(1)).findById(postId);
        verify(post, times(1)).apply(postDto);
        verify(postRepository, times(1)).save(post);
    }

    @Test
    public void Service_게시물_삭제_성공() {
        // GIVEN
        long postId = 1L;
        when(postServiceImpl.get(postId)).thenReturn(Optional.of(post));
        when(post.delete()).thenReturn(post);

        // WHEN
        postServiceImpl.softRemove(postId);

        // THEN
        verify(post, times(1)).delete();
        verify(postRepository, times(1)).save(post);
    }
}
