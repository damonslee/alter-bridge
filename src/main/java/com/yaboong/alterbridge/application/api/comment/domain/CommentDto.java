package com.yaboong.alterbridge.application.api.comment.domain;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.converter.EntitySupplier;
import com.yaboong.alterbridge.configuration.modelmapper.SingletonModelMapper;
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
public class CommentDto implements EntitySupplier<Post> {

    @NotEmpty
    String content;

    @NotNull
    @PositiveOrZero
    Long likeCount;

    @NotEmpty
    String status;

    @Override
    public Post toEntity() {
        return SingletonModelMapper.getInstance().map(this, Post.class);
    }
}
