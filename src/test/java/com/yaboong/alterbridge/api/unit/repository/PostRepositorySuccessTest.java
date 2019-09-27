package com.yaboong.alterbridge.api.unit.repository;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.application.common.type.Status;
import com.yaboong.alterbridge.common.TestDescription;
import com.yaboong.alterbridge.common.TestProfile;
import com.yaboong.alterbridge.configuration.jpa.JpaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * Created by yaboong on 2019-09-20
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@Import(JpaConfiguration.class)
@ActiveProfiles(TestProfile.TEST)
public class PostRepositorySuccessTest {

    @Autowired
    PostRepository postRepository;

    Status status = Status.NORMAL;

    @Test
    @TestDescription("postId 로 게시물 한개 조회하기 테스트")
    public void findPostAndCommentByPostId() {
        // GIVEN
        Long postId = 1L;

        // WHEN
        Optional<Post> postOptional = postRepository.findPostAndCommentByPostId(postId, status);

        // THEN
        assertTrue(postOptional.isPresent());
        assertNotNull(postOptional.get());
        postOptional.ifPresent(validatePostField);
    }

    @Test
    @TestDescription("게시물 목록 조회 페이징")
    public void findAllPostPaging() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 5);
        Status status = Status.NORMAL;

        // WHEN
        Page<Post> postPaging = postRepository.findAllPostPaging(pageable, status);
        List<Post> postList = postPaging.getContent();

        // THEN
        assertNotNull(postPaging);
        assertEquals(postPaging.getSize(), 5);

        assertThat(postList, is(not(empty())));
        assertEquals(postList.size(), 5);
        postList.forEach(validatePostField);
    }

    private Consumer<Post> validatePostField = post -> {
        assertNotNull(post.getPostId());
        assertEquals(post.getStatus(), status);
        assertNotNull(post.getTitle());
        assertNotNull(post.getCategory());
        assertNotNull(post.getContent());
        assertNotNull(post.getViewCount());
        assertNotNull(post.getLikeCount());
        assertNotNull(post.getCreatedAt());
        assertNotNull(post.getCreatedBy());
        assertNotNull(post.getModifiedAt());
        assertNotNull(post.getModifiedBy());
    };
}
