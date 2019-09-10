package com.yaboong.alterbridge.application.api.post.domain;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by yaboong on 2019-09-11
 */
@Component
public class PostValidator {

    public void validate(PostDto postDto, Errors errors) {
        if (postDto.getLikeCount() > postDto.getViewCount()) {
            errors.rejectValue("likeCount", "WrongValue", "Like counts cannot be greater than view counts");
            errors.rejectValue("viewCount", "WrongValue", "View counts cannot be less than like counts");
            errors.reject("wrongCounts", "counts are invalid");
        }
    }

}
