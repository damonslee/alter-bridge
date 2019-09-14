package com.yaboong.alterbridge.application.api.post.controller;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.domain.PostResource;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.service.PostService;
import com.yaboong.alterbridge.application.common.validation.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by yaboong on 2019-09-11
 */
@Controller
@RequestMapping(value = "/posts", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class PostController {

    private final DtoValidator dtoValidator;

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity getPost(@PathVariable Long id) {
        return postService.get(id)
                .map(post -> ResponseEntity.ok(PostResource.of(post)
                                .addLink(linkTo(PostController.class).slash(post.getPostId()).withSelfRel().withType(HttpMethod.GET.name()))
                                .addLink(linkTo(PostController.class).slash(post.getPostId()).withRel("update-post").withType(HttpMethod.PUT.name()))
                                .addLink(linkTo(PostController.class).slash(post.getPostId()).withRel("delete-post").withType(HttpMethod.DELETE.name()))
                                .addLink(new Link("/docs/index.html#resources-posts-get").withRel("profile"))
                ))
                .orElseGet(() -> ResponseEntity.notFound().build())
                ;
    }

    @PostMapping
    public ResponseEntity createPost(
            @RequestBody @Valid PostDto postDto,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        dtoValidator.validate(postDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        Post newPost = postService.create(postDto);
        ControllerLinkBuilder selfBuilder = linkTo(PostController.class);
        return ResponseEntity
                .created(selfBuilder.toUri())
                .body(PostResource.of(newPost)
                    .addLink(linkTo(PostController.class).withSelfRel().withType(HttpMethod.POST.name()))
                    .addLink(new Link("/docs/index.html#resources-create-post").withRel("profile"))
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostDto postDto,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        dtoValidator.validate(postDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors);
        }

        return postService
                .modify(id, postDto)
                .map(post -> ResponseEntity.ok(PostResource.of(post)
                                .addLink(linkTo(PostController.class).slash(post.getPostId()).withSelfRel().withType(HttpMethod.PUT.name()))
                                .addLink(new Link("/docs/index.html#resources-update-post").withRel("profile"))
                ))
                .orElseGet(() -> ResponseEntity.notFound().build())
                ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeletePost(@PathVariable Long id) {
        return postService
                .softRemove(id)
                .map(post -> ResponseEntity.ok(PostResource.of(post)
                                .addLink(linkTo(PostController.class).slash(post.getPostId()).withSelfRel().withType(HttpMethod.DELETE.name()))
                                .addLink(new Link("/docs/index.html#resources-delete-post").withRel("profile"))
                ))
                .orElseGet(() -> ResponseEntity.notFound().build())
                ;
    }

}
