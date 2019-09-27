package com.yaboong.alterbridge.common;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;

/**
 * Created by yaboong on 2019-09-27
 */
public class DataPostDto implements Input {

    public static PostDto newPostDto() {
        return buildPostDto();
    }

    private static PostDto buildPostDto() {
        return PostDto.builder()
                .category(CATEGORY)
                .title(TITLE)
                .content(CONTENT)
                .likeCount(0L)
                .viewCount(0L)
                .status(STATUS)
                .build();
    }

    public static Object[] invalidPostDtoObjects() {
        return new Object[] {
                // empty params
                PostDto.builder().build(),

                // invalid status
                PostDto.builder().status(null).title(TITLE).content(CONTENT).viewCount(0L).likeCount(0L).category(CATEGORY).build(),
                PostDto.builder().status(NONE).title(TITLE).content(CONTENT).viewCount(0L).likeCount(0L).category(CATEGORY).build(),

                // invalid title
                PostDto.builder().status(STATUS).title(null).content(CONTENT).viewCount(0L).likeCount(0L).category(CATEGORY).build(),
                PostDto.builder().status(STATUS).title(EMPTY_STRING).content(CONTENT).viewCount(0L).likeCount(0L).category(CATEGORY).build(),

                // invalid content
                PostDto.builder().status(STATUS).title(TITLE).content(null).viewCount(0L).likeCount(0L).category(CATEGORY).build(),
                PostDto.builder().status(STATUS).title(TITLE).content(EMPTY_STRING).viewCount(0L).likeCount(0L).category(CATEGORY).build(),

                // invalid category
                PostDto.builder().status(STATUS).title(TITLE).content(CONTENT).viewCount(0L).likeCount(0L).category(null).build(),
                PostDto.builder().status(STATUS).title(TITLE).content(CONTENT).viewCount(0L).likeCount(0L).category(EMPTY_STRING).build(),
                PostDto.builder().status(STATUS).title(TITLE).content(CONTENT).viewCount(0L).likeCount(0L).category(NONE).build(),

                // invalid count (likeCount > viewCount)
                PostDto.builder().status(STATUS).title(TITLE).content(CONTENT).viewCount(0L).likeCount(1L).category(CATEGORY).build()

        };
    }
}
