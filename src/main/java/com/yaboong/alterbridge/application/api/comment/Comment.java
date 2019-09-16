package com.yaboong.alterbridge.application.api.comment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.auditing.Auditable;
import com.yaboong.alterbridge.application.common.type.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
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
public class Comment extends Auditable<String> implements Function<CommentDto, Comment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long commentId;

    @ColumnDefault("0")
    Long likeCount;

    @ColumnDefault("'NORMAL'")
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    private Comment buildComment(CommentDto commentDto) {
        return Comment.builder()
                .status(Status.valueOf(commentDto.getStatus()))
                .content(commentDto.getContent())
                .likeCount(commentDto.getLikeCount())
                .build();
    }

}

