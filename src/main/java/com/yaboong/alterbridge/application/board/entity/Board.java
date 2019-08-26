package com.yaboong.alterbridge.application.board.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by yaboong on 2019-08-26
 */
@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue
    Integer id;
    String title;
    String contents;
    Integer hitCnt;
    String deletedYn;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updatedBy;
}
