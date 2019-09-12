package com.yaboong.alterbridge.application.api.board.service;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-12
 */
public interface BoardService<Entity, Dto> {

    Entity create(Dto dto);

    Optional<Entity> modify(Long id, Dto dto);

    Optional<Entity> softRemove(Long id);

    Optional<Entity> get(Long id);

    void remove(Long id);

}
