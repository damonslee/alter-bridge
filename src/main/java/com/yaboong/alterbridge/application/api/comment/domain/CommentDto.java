package com.yaboong.alterbridge.application.api.comment.domain;

import com.yaboong.alterbridge.application.api.comment.entity.Comment;
import com.yaboong.alterbridge.application.common.converter.EntitySupplier;
import com.yaboong.alterbridge.application.common.type.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

/**
 * Created by yaboong on 2019-09-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto implements EntitySupplier<Comment> {

    @NotEmpty
    String content;

    @NotNull
    @PositiveOrZero
    Long likeCount;

    @NotEmpty
    String status;

    @Override
    public Comment toEntity() {
        return Comment.builder()
                .content(this.content)
                .likeCount(this.likeCount)
                .status(Status.valueOf(this.status))
                .build();
    }
}
