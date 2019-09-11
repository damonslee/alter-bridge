package com.yaboong.alterbridge.application.api.boardfile.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by yaboong on 2019-09-11
 */
@Entity
@Table(name = "comment_file")
@DiscriminatorValue("COMMENT")
@Getter
@Setter
@AllArgsConstructor
public class CommentFile extends BoardFile {
}
