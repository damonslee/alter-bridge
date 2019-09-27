package com.yaboong.alterbridge.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.common.DataPostDto;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
public class PostIntegrationSuccessTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PostRepository postRepository;

    @Test
    @TestDescription(
            "정상적으로 게시물 목록 조회한 경우" +
            "다음 상태로 전이시킬 수 있는 페이징 관련 링크가 포함되어야 함"
    )
    public void PostController_게시물_목록조회페이징_200() throws Exception {
        // GIVEN
        int page = 1;
        int size = 1;

        // WHEN
        MockHttpServletRequestBuilder request = get("/posts?page={page}&size={size}", page, size)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE);

        // THEN
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))

                .andExpect(jsonPath("_embedded.postList[0].postId").exists())
                .andExpect(jsonPath("_embedded.postList[0]._links.profile.href").exists())
                .andExpect(jsonPath("_embedded.postList[0]._links.self.href").exists())
                .andExpect(jsonPath("_embedded.postList[0]._links.self.type").value(HttpMethod.GET.name()))
                .andExpect(jsonPath("_embedded.postList[0]._links.update-post.href").exists())
                .andExpect(jsonPath("_embedded.postList[0]._links.update-post.type").value(HttpMethod.PUT.name()))
                .andExpect(jsonPath("_embedded.postList[0]._links.delete-post.href").exists())
                .andExpect(jsonPath("_embedded.postList[0]._links.delete-post.type").value(HttpMethod.DELETE.name()))
                .andExpect(jsonPath("_links.self.href").exists())
                .andExpect(jsonPath("_links.profile.href").exists())
                .andExpect(jsonPath("_links.first.href").exists())
                .andExpect(jsonPath("_links.next.href").exists())
                .andExpect(jsonPath("_links.last.href").exists())
                .andExpect(jsonPath("page.size").exists())
                .andExpect(jsonPath("page.totalElements").exists())
                .andExpect(jsonPath("page.totalPages").exists())
                .andExpect(jsonPath("page.number").exists())

                .andDo(document("query-posts",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("profile").description("link to profile"),
                                linkWithRel("next").description("link to the next page"),
                                linkWithRel("last").description("link to the last page"),
                                linkWithRel("first").description("link to the first page"),
                                linkWithRel("prev").description("link to the previous page")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type header")
                        ),
                        responseFields(
                                fieldWithPath("_embedded.postList[0].@id").description("json identifier"),
                                fieldWithPath("_embedded.postList[0].createdBy").description("등록자"),
                                fieldWithPath("_embedded.postList[0].createdAt").description("등록일시 [yyyy-MM-ddTHH-mm-ss.zzz]"),
                                fieldWithPath("_embedded.postList[0].modifiedBy").description("수정자"),
                                fieldWithPath("_embedded.postList[0].modifiedAt").description("수정일시 [yyyy-MM-ddTHH-mm-ss.zzz]"),
                                fieldWithPath("_embedded.postList[0].postId").description("게시물 식별자"),
                                fieldWithPath("_embedded.postList[0].category").description("게시물 카테고리"),
                                fieldWithPath("_embedded.postList[0].title").description("게시물 제목"),
                                fieldWithPath("_embedded.postList[0].content").description("게시물 내용"),
                                fieldWithPath("_embedded.postList[0].viewCount").description("게시물 조회수"),
                                fieldWithPath("_embedded.postList[0].likeCount").description("게시물 좋아요 수"),
                                fieldWithPath("_embedded.postList[0].status").description("게시물 상태"),
                                fieldWithPath("_embedded.postList[0]._links.self.href").description("link to self"),
                                fieldWithPath("_embedded.postList[0]._links.self.type").description("http method for link to self"),
                                fieldWithPath("_embedded.postList[0]._links.update-post.href").description("link to update post"),
                                fieldWithPath("_embedded.postList[0]._links.update-post.type").description("http method for link to update post"),
                                fieldWithPath("_embedded.postList[0]._links.delete-post.href").description("link to delete post"),
                                fieldWithPath("_embedded.postList[0]._links.delete-post.type").description("http method for link to delete post"),
                                fieldWithPath("_embedded.postList[0]._links.profile.href").description("link to profile"),

                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.profile.href").description("link to profile"),
                                fieldWithPath("_links.first.href").description("link to the first page"),
                                fieldWithPath("_links.prev.href").description("link to the previous page"),
                                fieldWithPath("_links.next.href").description("link to the next page"),
                                fieldWithPath("_links.last.href").description("link to the last page"),

                                fieldWithPath("page.size").description("size of a page"),
                                fieldWithPath("page.totalElements").description("total number of elements"),
                                fieldWithPath("page.totalPages").description("total pages range. starts from 0"),
                                fieldWithPath("page.number").description("page index for the current request ")
                        )
                    )
                );
    }

    @Test
    @TestDescription("정상적으로 게시물을 조회한 경우")
    public void PostController_게시물_1개조회_200() throws Exception {
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

                // REST DOCS
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
                            fieldWithPath("_links.self.href").description("link to self"),
                            fieldWithPath("_links.self.type").description("http method for link to self"),
                            fieldWithPath("_links.update-post.href").description("link to update post"),
                            fieldWithPath("_links.update-post.type").description("http method for link to update post"),
                            fieldWithPath("_links.delete-post.href").description("link to delete post"),
                            fieldWithPath("_links.delete-post.type").description("http method for link to delete post"),
                            fieldWithPath("_links.profile.href").description("link to profile")
                        )
                    )
                );
    }

    @Test
    public void PostController_게시물_등록_201() throws Exception {
        // GIVEN
        PostDto newPostDto = DataPostDto.newPostDto();
        String newPostDtoJson = objectMapper.writeValueAsString(newPostDto);

        // WHEN
        MockHttpServletRequestBuilder request = post("/posts")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(newPostDtoJson);

        // THEN
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().exists(HttpHeaders.LOCATION))
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("postId").exists())
                .andExpect(jsonPath("category").isNotEmpty())
                .andExpect(jsonPath("status").isNotEmpty())
                .andExpect(jsonPath("title").isNotEmpty())
                .andExpect(jsonPath("content").isNotEmpty())
                .andExpect(jsonPath("viewCount").value(0))
                .andExpect(jsonPath("likeCount").value(0))
                .andExpect(jsonPath("createdBy").isNotEmpty())
                .andExpect(jsonPath("createdAt").isNotEmpty())
                .andExpect(jsonPath("modifiedBy").isNotEmpty())
                .andExpect(jsonPath("modifiedAt").isNotEmpty())
                .andExpect(jsonPath("_links.self").exists())
                .andExpect(jsonPath("_links.profile").exists())
                .andExpect(jsonPath("_links.post-list").exists())

                // REST DOCS
                .andDo(document("create-post",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("profile").description("link to profile"),
                                linkWithRel("post-list").description("link to post list")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                        ),
                        responseHeaders(
                                headerWithName(HttpHeaders.LOCATION).description("Location header"),
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
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.self.type").description("http method for link to self"),
                                fieldWithPath("_links.profile.href").description("link to profile"),
                                fieldWithPath("_links.post-list.href").description("link to query all posts"),
                                fieldWithPath("_links.post-list.type").description("http method for link to post list")
                        )
                    )
                );
    }

    @Test
    public void PostController_게시물_수정_200() throws Exception {
        // GIVEN
        Long postId = 1L;
        PostDto newPostDto = DataPostDto.newPostDto();
        String newPostDtoJson = objectMapper.writeValueAsString(newPostDto);

        // WHEN
        MockHttpServletRequestBuilder request = put("/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON)
                .content(newPostDtoJson);

        // THEN
        mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("postId").value(postId))
                .andExpect(jsonPath("category").value(newPostDto.getCategory()))
                .andExpect(jsonPath("status").value(newPostDto.getStatus()))
                .andExpect(jsonPath("title").value(newPostDto.getTitle()))
                .andExpect(jsonPath("content").value(newPostDto.getContent()))
                .andExpect(jsonPath("likeCount").value(newPostDto.getLikeCount()))
                .andExpect(jsonPath("viewCount").value(newPostDto.getViewCount()))
                .andExpect(jsonPath("_links.profile.href").exists())
                .andExpect(jsonPath("_links.self.href").exists())
                .andExpect(jsonPath("_links.self.type").value(HttpMethod.PUT.name()))
                .andExpect(jsonPath("_links.post-list.href").exists())
                .andExpect(jsonPath("_links.post-list.type").value(HttpMethod.GET.name()))
                .andDo(document("update-post",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("profile").description("link to profile"),
                                linkWithRel("post-list").description("link to post list")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
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
                                fieldWithPath("title").description("수정후 게시물 제목"),
                                fieldWithPath("content").description("수정후 게시물 내용"),
                                fieldWithPath("viewCount").description("수정후 게시물 조회수"),
                                fieldWithPath("likeCount").description("수정후 게시물 좋아요 수"),
                                fieldWithPath("status").description("수정후 게시물 상태"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.self.type").description("http method for link to self"),
                                fieldWithPath("_links.profile.href").description("link to profile"),
                                fieldWithPath("_links.post-list.href").description("link to query all posts"),
                                fieldWithPath("_links.post-list.type").description("http method for link to post list")
                        )
                    )
                )
        ;
    }

    @Test
    public void PostController_게시물_삭제_200() throws Exception {
        // GIVEN
        Long postId = 1L;

        // WHEN
        MockHttpServletRequestBuilder request = delete("/posts/{postId}", postId)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaTypes.HAL_JSON_UTF8_VALUE);

        // THEN
        this.mockMvc.perform(request)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
                .andExpect(jsonPath("postId").value(postId))
                .andExpect(jsonPath("status").value("DELETED"))
                .andExpect(jsonPath("_links.profile.href").isNotEmpty())
                .andExpect(jsonPath("_links.self.href").isNotEmpty())
                .andExpect(jsonPath("_links.self.type").value(HttpMethod.DELETE.name()))
                .andExpect(jsonPath("_links.post-list.href").isNotEmpty())
                .andExpect(jsonPath("_links.post-list.type").value(HttpMethod.GET.name()))
                .andDo(document("delete-post",
                        links(
                                linkWithRel("self").description("link to self"),
                                linkWithRel("profile").description("link to profile"),
                                linkWithRel("post-list").description("link to post list")
                        ),
                        requestHeaders(
                                headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                                headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
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
                                fieldWithPath("postId").description("삭제된 게시물 식별자"),
                                fieldWithPath("category").description("삭제된 게시물 카테고리"),
                                fieldWithPath("title").description("삭제된 게시물 제목"),
                                fieldWithPath("content").description("삭제된 게시물 내용"),
                                fieldWithPath("viewCount").description("삭제된 게시물 조회수"),
                                fieldWithPath("likeCount").description("삭제된 게시물 좋아요 수"),
                                fieldWithPath("status").description("삭제된 게시물 상태"),
                                fieldWithPath("_links.profile.href").description("link to profile"),
                                fieldWithPath("_links.self.href").description("link to self"),
                                fieldWithPath("_links.self.type").description("http method for link to self"),
                                fieldWithPath("_links.post-list.href").description("link to query all posts"),
                                fieldWithPath("_links.post-list.type").description("http method for link to post list")
                        )
                    )
                )
        ;
    }
}
