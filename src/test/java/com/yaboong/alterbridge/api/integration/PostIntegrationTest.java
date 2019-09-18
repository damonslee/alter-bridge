package com.yaboong.alterbridge.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.common.TestDataGenerator;
import com.yaboong.alterbridge.common.TestDescription;
import com.yaboong.alterbridge.common.TestProfile;
import com.yaboong.alterbridge.configuration.RestDocsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by yaboong on 2019-09-15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc // MockMvc 쓰기위해
@AutoConfigureRestDocs
@Import({RestDocsConfiguration.class})
@ActiveProfiles(TestProfile.TEST)
public class PostIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostRepository postRepository;

    @Test
    @TestDescription("정상적으로 게시물을 조회한 경우")
    public void 게시물_1개조회_200_통합테스트() throws Exception {
        // GIVEN - by import.sql
        long postId = 1L;

        // WHEN
        MockHttpServletRequestBuilder request = get("/posts/{id}", postId)
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
                .andExpect(jsonPath("_links.profile").exists())
                .andDo(document("posts-get",
                        links(
                            linkWithRel("self").description("link to self"),
                            linkWithRel("update-post").description("link to update post"),
                            linkWithRel("delete-post").description("link to delete post"),
                            linkWithRel("profile").description("link to profile")
                        ),
                        requestHeaders(
                            headerWithName(HttpHeaders.ACCEPT).description("accept header")
                        ),
                        responseHeaders(
                            headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
                        ),
                        responseFields(
                            fieldWithPath("@id").description("json identifier"),
                            fieldWithPath("createdBy").description("등록자"),
                            fieldWithPath("createdAt").description("등록일시 [yyyy-MM-ddTHH-mm-ss.zzz]"),
                            fieldWithPath("modifiedBy").description("수정자"),
                            fieldWithPath("modifiedAt").description("수정일시 [yyyy-MM-ddTHH-mm-ss.zzz]"),
                            fieldWithPath("postId").description("게시물 식별자"),
                            fieldWithPath("category").description("게시물 카테고리"),
                            fieldWithPath("title").description("게시물 제목"),
                            fieldWithPath("content").description("게시물 내용"),
                            fieldWithPath("viewCount").description("게시물 조회수"),
                            fieldWithPath("likeCount").description("게시물 좋아요 수"),
                            fieldWithPath("status").description("게시물 상태"),
                            fieldWithPath("comments").description("게시물에 달린 댓글 목록"),
                            fieldWithPath("_links.self.href").description("link to self"),
                            fieldWithPath("_links.self.type").description("http method for link to self"),
                            fieldWithPath("_links.update-post.href").description("link to update post"),
                            fieldWithPath("_links.update-post.type").description("http method for link to update post"),
                            fieldWithPath("_links.delete-post.href").description("link to delete post"),
                            fieldWithPath("_links.delete-post.type").description("http method for link to delete post"),
                            fieldWithPath("_links.profile.href").description("link to profile")
                        )
                    )
                )
        ;
    }
}
