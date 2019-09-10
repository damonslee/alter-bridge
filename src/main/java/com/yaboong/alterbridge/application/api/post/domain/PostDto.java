package com.yaboong.alterbridge.application.api.post.domain;

import com.yaboong.alterbridge.application.api.boardfile.entity.BoardFile;
import com.yaboong.alterbridge.application.api.comment.entity.Comment;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
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

    @Positive
    Long postId;

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

    List<Comment> comments;

    List<BoardFile> files;
}
