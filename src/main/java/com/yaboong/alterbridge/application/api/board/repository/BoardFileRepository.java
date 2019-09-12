package com.yaboong.alterbridge.application.api.board.repository;

import com.yaboong.alterbridge.application.api.board.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yaboong on 2019-09-10
 */
public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
}
