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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    @TestDescription(
            "페이징 size 파라미터로 큰 값을 주어도" +
            "프로퍼티에 spring.data.web.pageable.max-page-size 값에 지정한 만큼만 나와야함"
    )
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
            "게시물 생성시" +
            "request body json 필드가 null 값을 가지는 경우" +
            "응답에 rejectedValue 필드가 포함되지 않음"
    )
    @Parameters(method = "inputContainsNullField")
    public void 게시물생성_null_필드를_가지는경우_400(PostDto postDto) throws Exception {
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
                .andDo(document("error-create-update-post-null-param",
                        links(
                                linkWithRel("profile").description("link to profile")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type header")
                        ),
                        responseFields(
                                fieldWithPath("content[0].field").description("error field"),
                                fieldWithPath("content[0].code").description("error field restriction"),
                                fieldWithPath("content[0].defaultMessage").description("message about the cause of the error"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                    )
                )
        ;
    }

    @Test
    @TestDescription(
            "게시물 생성시" +
            "request body json 필드가 유효하지 않은 값을 가지는 경우" +
            "응답에 rejectedValue 필드가 포함됨"
    )
    @Parameters(method = "inputContainsInvalidFieldValue")
    public void 게시물생성_유효하지_않은_파라미터_400(PostDto postDto) throws Exception {
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
                .andDo(document("error-create-update-post-invalid-param",
                        links(
                                linkWithRel("profile").description("link to profile")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content-Type header")
                        ),
                        responseFields(
                                fieldWithPath("content[0].field").description("error field"),
                                fieldWithPath("content[0].code").description("error field restriction"),
                                fieldWithPath("content[0].defaultMessage").description("message about the cause of the error"),
                                fieldWithPath("content[0].rejectedValue").description("rejected field value of the request body"),
                                fieldWithPath("_links.profile.href").description("link to profile")
                        )
                    )
                )
        ;
    }

    @Test
    @TestDescription("존재하지 않는 게시물 수정시 실패")
    public void 존재하지_않는_게시물_수정() throws Exception {
        // GIVEN
        Long postId = Long.MAX_VALUE;
        String postDtoJson = objectMapper.writeValueAsString(DataPostDto.newPostDto());

        // WHEN
        MockHttpServletRequestBuilder request = put("/posts/{id}", postId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(postDtoJson);

        // THEN
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @TestDescription(
            "게시물 수정시" +
            "request body json 필드가 null 값을 가지는 경우" +
            "응답에 rejectedValue 필드가 포함되지 않음"
    )
    @Parameters(method = "inputContainsNullField")
    public void 게시물수정_null_필드를_가지는경우_400(PostDto postDto) throws Exception {
        // GIVEN
        Long postId = 1L;
        String postDtoJson = objectMapper.writeValueAsString(postDto);

        // WHEN
        MockHttpServletRequestBuilder request = put("/posts/{id}", postId)
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
                .andExpect(jsonPath("_links.profile.href").exists());
    }

    @Test
    @TestDescription(
            "게시물 수정시" +
            "request body json 필드가 유효하지 않은 값을 가지는 경우" +
            "응답에 rejectedValue 필드가 포함됨"
    )
    @Parameters(method = "inputContainsInvalidFieldValue")
    public void 게시물수정_유효하지_않은_파라미터_400(PostDto postDto) throws Exception {
        // GIVEN
        Long postId = 1L;
        String postDtoJson = objectMapper.writeValueAsString(postDto);

        // WHEN
        MockHttpServletRequestBuilder request = put("/posts/{id}", postId)
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
                .andExpect(jsonPath("_links.profile.href").exists());
    }


    public Object inputContainsInvalidFieldValue() {
        return DataPostDto.containsInvalidFieldValue();
    }

    public Object inputContainsNullField() {
        return DataPostDto.containsNullParams();
    }
}
