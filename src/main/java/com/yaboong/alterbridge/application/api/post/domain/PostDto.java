package com.yaboong.alterbridge.application.api.post.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * Created by yaboong on 2019-09-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostDto {

    @NotEmpty
    String category;

    @NotEmpty
    String title;

    @NotEmpty
    String content;

    @PositiveOrZero
    Long viewCount;

    @PositiveOrZero
    Long likeCount;

    String deletedYn;

    List<String> files;

}
