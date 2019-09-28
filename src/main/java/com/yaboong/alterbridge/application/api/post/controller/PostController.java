package com.yaboong.alterbridge.application.api.post.controller;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.service.PostService;
import com.yaboong.alterbridge.application.common.validation.PostDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.yaboong.alterbridge.application.api.post.link.PostLink.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by yaboong on 2019-09-11
 */
@Controller
@RequestMapping(value = "/posts", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class PostController {

    private final PostDtoValidator postDtoValidator;

    private final PostService postService;

    @GetMapping
    public ResponseEntity getPostList(Pageable pageable, PagedResourcesAssembler<Post> pagedResourcesAssembler) {
        Page<Post> postPagedList = postService.getList(pageable);
        return ResponseEntity.ok(
                addLinksForPostPagedList(pagedResourcesAssembler, postPagedList)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity getPost(@PathVariable Long id) {
        return postService
                .get(id)
                .map(addLinksForGetPost)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity createPost(
            @RequestBody @Valid PostDto postDto,
            Errors errors
    ) {
        return postDtoValidator
                .hasErrors(postDto, errors)
                .orElseGet(() -> {
                    Post post = postService.create(postDto);
                    return ResponseEntity
                            .created(linkTo(PostController.class).toUri())
                            .body(addLinksForCreatePost(post));
                });
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostDto postDto,
            Errors errors
    ) {
        return postDtoValidator
                .hasErrors(postDto, errors)
                .orElseGet(() ->
                        postService.modify(id, postDto)
                                .map(addLinksForUpdatePost)
                                .orElse(ResponseEntity.notFound().build())
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeletePost(@PathVariable Long id) {
        return postService
                .softRemove(id)
                .map(addLinksForDeletePost)
                .orElse(ResponseEntity.notFound().build());
    }
}
