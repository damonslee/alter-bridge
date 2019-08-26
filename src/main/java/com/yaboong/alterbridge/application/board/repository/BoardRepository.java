package com.yaboong.alterbridge.application.board.repository;

import com.yaboong.alterbridge.application.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by yaboong on 2019-08-26
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
}
