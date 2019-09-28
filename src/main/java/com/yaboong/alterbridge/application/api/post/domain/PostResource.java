package com.yaboong.alterbridge.application.api.post.domain;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

/**
 * Created by yaboong on 2019-09-14
 */
public class PostResource extends Resource<Post> {
    private PostResource(Post post, Link... links) {
        super(post, links);
    }

    public static PostResource of(Post post) {
        return new PostResource(post);
    }

    public PostResource addLink(Link link) {
        add(link);
        return this;
    }

}
