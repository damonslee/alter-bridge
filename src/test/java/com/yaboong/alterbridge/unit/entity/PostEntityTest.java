package com.yaboong.alterbridge.unit.entity;

import com.yaboong.alterbridge.TestProfile;
import com.yaboong.alterbridge.application.api.post.Post;
import com.yaboong.alterbridge.application.api.post.PostRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yaboong on 2019-08-29.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(TestProfile.TEST)
public class PostEntityTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    PostRepository postRepository;

    @Test
    public void 게시글_등록() {
        Post post = Post.builder()
                .title("등록테스트입니다.")
                .category("개발")
                .content("내용입니다.")
                .createdBy("yaboong")
                .build();

        postRepository.save(post);
        assertThat(postRepository.getOne(post.getPostId()), is(post));
    }
}
