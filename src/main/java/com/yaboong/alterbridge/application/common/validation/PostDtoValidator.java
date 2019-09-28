package com.yaboong.alterbridge.application.common.validation;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.error.ErrorResource;
import com.yaboong.alterbridge.application.common.type.Status;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-11
 */
@Component
public class PostDtoValidator {

    public Optional<ResponseEntity> hasErrors(PostDto postDto, Errors errors) {
        if (errors.hasErrors()) {
            return Optional.of(
                    ResponseEntity.badRequest().body(
                            ErrorResource.of(errors).addLink(new Link("/docs/index.html#error-post-null-param").withRel("profile"))
                    )
            );
        }

        validate(postDto, errors);
        if (errors.hasErrors()) {
            return Optional.of(
                    ResponseEntity.badRequest().body(
                            ErrorResource.of(errors).addLink(new Link("/docs/index.html#error-post-invalid-param").withRel("profile"))
                    )
            );
        }

        return Optional.empty();
    }

    private void validate(PostDto postDto, Errors errors) {
        likeCountCannotExceedViewCount(postDto, errors);
        postCategoryValidation(postDto, errors);
        postStatusValidation(postDto, errors);
    }

    private void likeCountCannotExceedViewCount(PostDto postDto, Errors errors) {
        Long viewCount = postDto.getViewCount();
        Long likeCount = postDto.getLikeCount();
        boolean invalidCount = likeCount > viewCount;
        if (invalidCount) {
            errors.rejectValue("likeCount", "WrongValue", "Like counts cannot be greater than view counts");
            errors.rejectValue("viewCount", "WrongValue", "View counts cannot be less than like counts");
        }
    }

    private void postCategoryValidation(PostDto postDto, Errors errors) {
        String category = postDto.getCategory();
        boolean isValidCategory = EnumUtils.isValidEnum(Post.Category.class, category);
        if (!isValidCategory) {
            errors.rejectValue("category", "invalidCategory", "Requested category of post not exists");
        }
    }

    private void postStatusValidation(PostDto postDto, Errors errors) {
        String status = postDto.getStatus();
        boolean isValidStatus = EnumUtils.isValidEnum(Status.class, status);
        if (!isValidStatus) {
            errors.rejectValue("status", "invalidStatus", "Requested status of post not exists");
        }
    }

}
