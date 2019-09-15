package com.yaboong.alterbridge.api.unit.service;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.application.api.post.service.PostServiceImpl;
import com.yaboong.alterbridge.common.TestDataGenerator;
import com.yaboong.alterbridge.common.TestProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.test.context.ActiveProfiles;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

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

    @Mock
    ModelMapper modelMapper;

    @Test
    public void 게시물_생성_성공() {
        // GIVEN
        PostDto postDto = TestDataGenerator.newPostDto();

        // WHEN
        postServiceImpl.create(postDto);

        // THEN
        InOrder inOrder = inOrder(modelMapper, postRepository);
        inOrder.verify(modelMapper, times(1)).map(any(), any());
        inOrder.verify(postRepository, times(1)).save(any());
    }
}
