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
                .status(NORMAL)
                .build();
    }
}
