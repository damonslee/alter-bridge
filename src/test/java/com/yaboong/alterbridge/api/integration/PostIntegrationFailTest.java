package com.yaboong.alterbridge.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
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
     * JUnit 에서 한번에 하나의 Runner 만 지원하기 때문에 JUnitParamRunner 도 사용하고, SpringRunner 도 사용하려면 일단 이렇게
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
    PostRepository postRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void PostController_Pageable_Size_제한() throws Exception {
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
    @TestDescription("잘못된 파라미터를 받는 경우 400 응답")
    @Parameters(method = "inputParamsInvalidPostDto")
    public void PostController_유효하지_않은_파라미터_400(PostDto postDto) throws Exception {
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
                .andExpect(status().isBadRequest());
    }

    public Object inputParamsInvalidPostDto() {
        return new Object[] {
                PostDto.builder().status(null).title("").content("").viewCount(0L).likeCount(0L).category("GENERAL").build()
        };
    }
}
