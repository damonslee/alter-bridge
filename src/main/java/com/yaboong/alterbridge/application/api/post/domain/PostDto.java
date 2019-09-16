package com.yaboong.alterbridge.application.api.post.domain;

import com.yaboong.alterbridge.application.api.post.entity.Post;
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
public class PostDto implements EntitySupplier<Post> {

    @NotEmpty
    String category;

    @NotEmpty
    String title;

    @NotEmpty
    String content;

    @NotNull
    @PositiveOrZero
    Long viewCount;

    @NotNull
    @PositiveOrZero
    Long likeCount;

    @NotEmpty
    String status;

    @Override
    public Post toEntity() {
        return Post.builder()
                .category(Post.Category.valueOf(this.category))
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .likeCount(this.likeCount)
                .status(Status.valueOf(this.status))
                .build();
    }
}
