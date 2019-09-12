package com.yaboong.alterbridge.application.api.comment.entity;

import com.yaboong.alterbridge.application.api.board.entity.BoardFile;
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
