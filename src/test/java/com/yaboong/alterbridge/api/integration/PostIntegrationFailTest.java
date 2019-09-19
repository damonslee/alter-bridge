package com.yaboong.alterbridge.api.integration;

import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.common.TestProfile;
import com.yaboong.alterbridge.configuration.RestDocsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by yaboong on 2019-09-19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import({RestDocsConfiguration.class})
@ActiveProfiles(TestProfile.TEST)
public class PostIntegrationFailTest {

    @Value("${spring.data.web.pageable.max-page-size}")
    Long MAX_PAGE_SIZE;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PostRepository postRepository;

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
                .andExpect(jsonPath("page.size").value(MAX_PAGE_SIZE))
        ;

    }
}
