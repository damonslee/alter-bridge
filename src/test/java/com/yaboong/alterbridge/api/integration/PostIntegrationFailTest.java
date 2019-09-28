package com.yaboong.alterbridge.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.common.DataPostDto;
import com.yaboong.alterbridge.common.TestDescription;
import com.yaboong.alterbridge.common.TestProfile;
import com.yaboong.alterbridge.configuration.RestDocsConfiguration;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by yaboong on 2019-09-19
 */
@RunWith(JUnitParamsRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import({RestDocsConfiguration.class})
@ActiveProfiles(TestProfile.TEST)
public class PostIntegrationFailTest {

    /**
     * SpringClassRule, SpringMethodRule 이 @RunWith(SpringRunner.class) 를 대체함
     * JUnit 에서 한번에 하나의 Runner 만 지원하기 때문에 JUnitParamRunner 도 사용하고, SpringRunner 도 사용하려면 일단 이렇게 해야함
     *
     * 2019.09.28 시점
     * JUnitParams runner must be top junit runner. Combining it with spring was impossible until now.
     * https://github.com/Pragmatists/junitparams-spring-integration-example
     */
    @ClassRule
    public static final SpringClassRule springClassRule = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Value("${spring.data.web.pageable.max-page-size}")
    Long MAX_PAGE_SIZE;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void Pageable_Size_제한() throws Exception {
        // GIVEN
        MockHttpServletRequestBuilder request = get("/posts")
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE)
                .param("page", "0")
                .param("size", "100");

        // WHEN & THEN
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("page").exists())
                .andExpect(jsonPath("page.size").value(MAX_PAGE_SIZE));
    }


    @Test
    @TestDescription(
            "request body json 필드가 null 값을 가지는 경우" +
            "응답에 rejectedValue 필드가 포함되지 않음"
    )
    @Parameters(method = "inputContainsNullField")
    public void Null_필드를_가지는경우_400(PostDto postDto) throws Exception {
        // GIVEN - by param
        String postDtoJson = objectMapper.writeValueAsString(postDto);

        // WHEN
        MockHttpServletRequestBuilder request = post("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(postDtoJson);

        // THEN
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("content[0].field").exists())
                .andExpect(jsonPath("content[0].code").exists())
                .andExpect(jsonPath("content[0].defaultMessage").exists())
                .andExpect(jsonPath("_links.profile.href").exists())
        ;
    }

    @Test
    @TestDescription(
            "request body json 필드가 유효하지 않은 값을 가지는 경우" +
            "응답에 rejectedValue 필드가 포함됨"
    )
    @Parameters(method = "inputContainsInvalidFieldValue")
    public void 유효하지_않은_파라미터로_게시물생성_400(PostDto postDto) throws Exception {
        // GIVEN - by param
        String postDtoJson = objectMapper.writeValueAsString(postDto);

        // WHEN
        MockHttpServletRequestBuilder request = post("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(postDtoJson);

        // THEN
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("content[0].field").exists())
                .andExpect(jsonPath("content[0].code").exists())
                .andExpect(jsonPath("content[0].defaultMessage").exists())
                .andExpect(jsonPath("content[0].rejectedValue").exists())
                .andExpect(jsonPath("_links.profile.href").exists())
        ;
    }

    @Test
    @TestDescription("없는 게시물을 조회하는 경우 404 NOT FOUND 응답")
    public void 게시물_1개조회_404() throws Exception {
        // GIVEN
        Long postId = Long.MAX_VALUE;

        // WHEN
        MockHttpServletRequestBuilder request = get("/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE);

        // THEN
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    public Object inputContainsInvalidFieldValue() {
        return DataPostDto.containsInvalidFieldValue();
    }

    public Object inputContainsNullField() {
        return DataPostDto.containsNullParams();
    }
}
