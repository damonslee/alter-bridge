package com.yaboong.alterbridge.unit.entity;

import com.yaboong.alterbridge.TestProfile;
import com.yaboong.alterbridge.application.api.comment.Comment;
import com.yaboong.alterbridge.application.api.comment.CommentRepository;
import com.yaboong.alterbridge.application.api.post.Post;
import com.yaboong.alterbridge.configuration.jpa.JpaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by yaboong on 2019-08-29.
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@Import(JpaConfiguration.class)
@ActiveProfiles(TestProfile.TEST)
public class CommentEntityTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void 댓글_등록() {
        // GIVEN
        Post post = Post.builder()
                .title("등록테스트입니다.")
                .category("개발")
                .content("내용입니다.")
                .build();
        testEntityManager.persist(post);

        // WHEN
        Comment comment = Comment.builder()
                .post(post)
                .content("테스트 댓글")
                .build();
        commentRepository.save(comment);

        // THEN
        assertThat(commentRepository.getOne(comment.getCommentId()), is(comment));
    }

}
