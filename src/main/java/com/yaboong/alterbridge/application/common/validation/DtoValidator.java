package com.yaboong.alterbridge.application.common.validation;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Created by yaboong on 2019-09-11
 */
@Component
public class DtoValidator {

    public void validate(PostDto postDto, Errors errors) {
        if (postDto.getLikeCount() > postDto.getViewCount()) {
            errors.rejectValue("likeCount", "WrongValue", "Like counts cannot be greater than view counts");
            errors.rejectValue("viewCount", "WrongValue", "View counts cannot be less than like counts");
            errors.reject("wrongCounts", "counts are invalid");
        }
    }

}
