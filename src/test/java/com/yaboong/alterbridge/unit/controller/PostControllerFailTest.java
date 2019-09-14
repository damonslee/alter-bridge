package com.yaboong.alterbridge.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaboong.alterbridge.application.api.post.controller.PostController;
import com.yaboong.alterbridge.application.api.post.service.PostService;
import com.yaboong.alterbridge.application.common.validation.DtoValidator;
import com.yaboong.alterbridge.common.annotation.TestDescription;
import com.yaboong.alterbridge.common.annotation.TestProfile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yaboong on 2019-09-14
 */
@RunWith(SpringRunner.class)
@WebMvcTest(PostController.class)
@ActiveProfiles(TestProfile.TEST)
public class PostControllerFailTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    PostService postService;

    @MockBean
    DtoValidator dtoValidator;

    @Test
    @TestDescription("없는 게시물을 조회하는 경우 404 NOT FOUND 응답")
    public void 게시물_1개조회_404() throws Exception {
        // GIVEN
        when(postService.get(1L)).thenReturn(Optional.empty());

        // WHEN
        MockHttpServletRequestBuilder request = get("/posts/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE);

        // THEN
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }
}
