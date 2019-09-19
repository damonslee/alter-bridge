package com.yaboong.alterbridge.api.unit.service;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.application.api.post.service.PostServiceImpl;
import com.yaboong.alterbridge.application.common.type.Status;
import com.yaboong.alterbridge.common.TestProfile;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
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

    Long postId;

    @Before
    public void init() {
        post = mock(Post.class);
        postDto = mock(PostDto.class);
        postId = 1L;
    }

    @Test
    public void PostServiceImpl_getList_성공() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 5);
        Status status = Status.NORMAL;
        when(postRepository.findAllPostPaging(pageable, status))
                .thenReturn(new PageImpl<>(new ArrayList<>(), pageable, 10L));

        // WHEN
        Page<Post> postList = postServiceImpl.getList(pageable);

        // THEN
        assertEquals(postList.getSize(), 5);
        assertEquals(postList.getTotalPages(), 2);
        assertEquals(postList.getNumber(), 0);
        assertEquals(postList.getNumberOfElements(), 0);
        TestCase.assertEquals(postList.getPageable(), pageable);
        assertEquals(postList.getSort(), Sort.unsorted());
    }


    @Test
    public void PostServiceImpl_create_성공() {
        // WHEN
        postServiceImpl.create(postDto);

        // THEN
        verify(postRepository, times(1)).save(any());
    }

    @Test
    public void PostServiceImpl_modify_성공() {
        // GIVEN
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
    public void PostServiceImpl_softRemove_성공() {
        // GIVEN
        when(postServiceImpl.get(postId)).thenReturn(Optional.of(post));
        when(post.delete()).thenReturn(post);

        // WHEN
        postServiceImpl.softRemove(postId);

        // THEN
        verify(post, times(1)).delete();
        verify(postRepository, times(1)).save(post);
    }

    @Test
    public void PostServiceImpl_get_성공() {
        // GIVEN
        Status status = Status.NORMAL;
        when(postRepository.findPostAndCommentByPostId(postId, status)).thenReturn(Optional.of(post));

        // WHEN
        postServiceImpl.get(postId);

        // THEN
        verify(postRepository, times(1)).findPostAndCommentByPostId(postId, status);
    }
}
