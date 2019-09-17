package com.yaboong.alterbridge.api.unit.repository;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.application.common.type.Status;
import com.yaboong.alterbridge.common.TestDataGenerator;
import com.yaboong.alterbridge.common.TestProfile;
import com.yaboong.alterbridge.configuration.jpa.JpaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.isNull;

/**
 * Created by yaboong on 2019-09-18
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@Import(JpaConfiguration.class)
@ActiveProfiles(TestProfile.TEST)
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Test
    public void 게시물_조회_없는게시물조회시_Optional반환() {
        // GIVEN
        Post post = TestDataGenerator.newPost(postRepository, 1);

        // WHEN
        Optional<Post> postOptional = postRepository.findPostAndCommentByPostId(100L, Status.NORMAL);

        // THEN
        assertTrue(postOptional.isEmpty());
    }
}
