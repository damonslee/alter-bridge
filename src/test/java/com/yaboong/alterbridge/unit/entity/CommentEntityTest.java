package com.yaboong.alterbridge.unit.entity;

import com.yaboong.alterbridge.TestProfile;
import com.yaboong.alterbridge.application.api.comment.Comment;
import com.yaboong.alterbridge.application.api.comment.CommentRepository;
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
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by yaboong on 2019-08-29.
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(TestProfile.TEST)
public class CommentEntityTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void 댓글_등록() {
        Post post = Post.builder()
                .title("등록테스트입니다.")
                .category("개발")
                .content("내용입니다.")
                .createdBy("yaboong")
                .build();
        testEntityManager.persist(post);

        Comment comment = Comment.builder()
                .postId(post.getPostId())
                .content("테스트 댓글")
                .createdBy("oyabun")
                .build();
        commentRepository.save(comment);
        assertThat(commentRepository.getOne(comment.getCommentId()), is(comment));
    }
}
