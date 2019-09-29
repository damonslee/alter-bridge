package com.yaboong.alterbridge.application.api.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.auditing.Auditable;
import com.yaboong.alterbridge.application.common.type.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.function.Function;

/**
 * Created by yaboong on 2019-09-16
 */
@Entity
@Table(name = "comment")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "commentId", callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DynamicInsert @DynamicUpdate
@JsonIgnoreProperties("post")
public class Comment extends Auditable<String> implements Function<CommentDto, Comment>, Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long commentId;

    @ColumnDefault("0")
    Long likeCount;

    @ColumnDefault("'NORMAL'")
    @Enumerated(value = EnumType.STRING)
    Status status;

    @Column(nullable = false)
    String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postId")
    Post post;

    public void addPost(Post post) {
        this.post = post;
    }

    public Comment delete() {
        this.status = Status.DELETED;
        return this;
    }

    @Override
    public Comment apply(CommentDto commentDto) {
        return buildComment(commentDto);
    }

    private Comment buildComment(CommentDto commentDto) {
        return Comment.builder()
                .status(Status.valueOf(commentDto.getStatus()))
                .content(commentDto.getContent())
                .likeCount(commentDto.getLikeCount())
                .build();
    }

    @Override
    @JsonIgnore
    public Serializable getId() {
        return this.commentId;
    }
}

