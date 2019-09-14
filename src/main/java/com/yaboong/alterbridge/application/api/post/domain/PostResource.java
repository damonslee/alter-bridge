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
    private PostResource(Post post, Link... links) {
        super(post, links);
        addDefaultLinks(post);
    }

    public static PostResource of(Post post) {
        return new PostResource(post);
    }

    public PostResource addLink(Link link) {
        add(link);
        return this;
    }

    private void addDefaultLinks(Post post) {
        add(
//            linkTo(PostController.class).withRel("get-post-list").withType(HttpMethod.GET.name()),
//            linkTo(PostController.class).withRel("create-post").withType(HttpMethod.GET.name()),
//            linkTo(PostController.class).slash(post.getPostId()).withRel("get-post").withType(HttpMethod.GET.name()),
//            linkTo(PostController.class).slash(post.getPostId()).withRel("update-post").withType(HttpMethod.PUT.name()),
//            linkTo(PostController.class).slash(post.getPostId()).withRel("delete-post").withType(HttpMethod.DELETE.name())
        );
    }
}
