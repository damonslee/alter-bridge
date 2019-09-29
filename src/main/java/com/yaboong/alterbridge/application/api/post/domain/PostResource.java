package com.yaboong.alterbridge.application.api.post.domain;

import com.yaboong.alterbridge.application.api.post.controller.PostController;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by yaboong on 2019-09-14
 */
public class PostResource extends Resource<Post> {

    private static final String PROFILE_LINK_PREFIX = "/docs/index.html#";

    private final Post post;

    private PostResource(Post post, Link... links) {
        super(post, links);
        this.post = post;
    }

    public static PostResource of(Post post) {
        return new PostResource(post);
    }

    public static Link getProfileLink(String anchor) {
        return new Link(PROFILE_LINK_PREFIX+ anchor).withRel("profile");
    }

    public PostResource profile(String anchor) {
        add(new Link(PROFILE_LINK_PREFIX+ anchor).withRel("profile"));
        return this;
    }

    public PostResource self(HttpMethod httpMethod) {
        add(linkTo(PostController.class).withSelfRel().withType(httpMethod.name()));
        return this;
    }

    public PostResource selfWithId(HttpMethod httpMethod) {
        add(linkTo(PostController.class).slash(this.post.getPostId()).withSelfRel().withType(httpMethod.name()));
        return this;
    }

    public PostResource rel(HttpMethod httpMethod, String rel) {
        add(linkTo(PostController.class).withRel(rel).withType(httpMethod.name()));
        return this;
    }

    public PostResource relWithId(HttpMethod httpMethod, String rel) {
        add(linkTo(PostController.class).slash(this.post.getPostId()).withRel(rel).withType(httpMethod.name()));
        return this;
    }

}
