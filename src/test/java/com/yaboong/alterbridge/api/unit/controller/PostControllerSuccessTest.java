package com.yaboong.alterbridge.api.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaboong.alterbridge.application.api.post.controller.PostController;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.service.PostService;
import com.yaboong.alterbridge.application.common.type.Status;
import com.yaboong.alterbridge.application.common.validation.DtoValidator;
import com.yaboong.alterbridge.common.TestDescription;
import com.yaboong.alterbridge.common.TestProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by yaboong on 2019-09-14
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
@ActiveProfiles(TestProfile.TEST)
public class PostControllerSuccessTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    @MockBean
    DtoValidator dtoValidator;

    @Test
    @TestDescription("정상적으로 게시물을 조회한 경우")
    public void Controller_게시물_1개조회_200() throws Exception {
        // GIVEN
        Post post = Post.builder()
                .postId(1L)
                .category(Post.Category.GENERAL)
                .title("test post title #1")
                .content("test post content #1")
                .likeCount(0L)
                .viewCount(0L)
                .status(Status.NORMAL)
                .build();
        when(postService.get(1L)).thenReturn(Optional.of(post));

        // WHEN
        MockHttpServletRequestBuilder request = get("/posts/{id}", post.getPostId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE);

        // THEN
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("postId").exists())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.self.type").value(HttpMethod.GET.name()))
                .andExpect(jsonPath("_links.profile").exists());
    }

}
