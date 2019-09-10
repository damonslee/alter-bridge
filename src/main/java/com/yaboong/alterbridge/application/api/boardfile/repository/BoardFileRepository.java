package com.yaboong.alterbridge.application.api.boardfile.repository;

import com.yaboong.alterbridge.application.api.boardfile.entity.BoardFile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yaboong on 2019-09-10
 */
public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
}
