package com.yaboong.alterbridge.application.api.boardfile.entity;

import com.yaboong.alterbridge.application.common.auditing.Auditable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

/**
 * Created by yaboong on 2019-09-11
 */
@Entity
@Table(name = "board_file")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@FieldDefaults(level = AccessLevel.PROTECTED)
@DiscriminatorColumn(name = "board_type")
@Getter @Setter
public abstract class BoardFile extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long fileId;

    String path;
}
