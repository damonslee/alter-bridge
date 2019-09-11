package com.yaboong.alterbridge.application.api.post.entity;

import com.yaboong.alterbridge.application.api.boardfile.entity.BoardFile;
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
@Table(name = "post_file")
@DiscriminatorValue("POST")
@Getter
@Setter
@AllArgsConstructor
public class PostFile extends BoardFile {
}
