package com.yaboong.alterbridge.api.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaboong.alterbridge.application.api.post.controller.PostController;
import com.yaboong.alterbridge.application.api.post.service.PostService;
import com.yaboong.alterbridge.application.common.validation.DtoValidator;
import com.yaboong.alterbridge.common.TestDescription;
import com.yaboong.alterbridge.common.TestProfile;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yaboong on 2019-09-14
 */
@RunWith(JUnitParamsRunner.class)
@WebMvcTest(PostController.class)
@ActiveProfiles(TestProfile.TEST)
public class PostControllerFailTest {

    /**
     * SpringClassRule, SpringMethodRule 이 @RunWith(SpringRunner.class) 를 대체함
     * JUnit 에서 한번에 하나의 Runner 만 지원하기 때문에 JUnitParamRunner 도 사용하고, SpringRunner 도 사용하려면 일단 이렇게
     */
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

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
    public void Controller_게시물_1개조회_404() throws Exception {
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

    @Test
    @TestDescription("잘못된 파라미터를 받는 경우 400 응답")
    @Parameters(method = "failInputParams")
    public void 파라미터_받는_테스트메서드_예제(String input, String expected) throws Exception {
        assertEquals(input, expected);
    }

    public Object failInputParams() {
        return new Object[] {
                new Object[]{"test", "test"}
        };
    }

}
