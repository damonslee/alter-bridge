package com.yaboong.alterbridge.application.board.repository;

import com.yaboong.alterbridge.TestProfile;
import com.yaboong.alterbridge.application.board.entity.Board;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by yaboong on 2019-08-27
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles(TestProfile.Test)
public class BoardRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void 등록테스트() {
        Board board = Board.builder()
                .title("TITLE")
                .contents("THIS IS CONTENTS")
                .hitCnt(1)
                .createdBy("TEST CODE")
                .createdAt(LocalDateTime.now())
                .build();

        testEntityManager.persist(board);
        assertThat(boardRepository.getOne(board.getId()), is(board));
    }
}
