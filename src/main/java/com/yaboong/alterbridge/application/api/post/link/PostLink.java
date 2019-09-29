package com.yaboong.alterbridge.application.api.post.link;

import com.yaboong.alterbridge.application.api.post.domain.PostResource;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

/**
 * Created by yaboong on 2019-09-28
 */
public class PostLink {

    private PostLink() {}

    public static PagedResources addLinksForPostPagedList(
            PagedResourcesAssembler<Post> pagedResourcesAssembler,
            Page<Post> postPagedList
    ) {
        PagedResources<PostResource> postPagedResources = pagedResourcesAssembler.toResource(postPagedList, postPagingResourceAssembler);
        postPagedResources.add(PostResource.getProfileLink("resources-query-posts"));
        return postPagedResources;
    }

    public static PostResource addLinksForCreatePost(Post post) {
        return PostResource.of(post)
                .self(HttpMethod.POST)
                .profile("resources-create-post")
                .rel(HttpMethod.GET, "post-list");
    }

    public static Function<Post, ResponseEntity> addLinksForUpdatePost =
            post -> ResponseEntity.ok(
                        PostResource.of(post)
                            .selfWithId(HttpMethod.PUT)
                            .profile("resources-update-post")
                            .rel(HttpMethod.GET, "post-list")
                        );

    public static Function<Post, ResponseEntity> addLinksForGetPost =
            post -> ResponseEntity.ok(
                        PostResource.of(post)
                            .selfWithId(HttpMethod.GET)
                            .relWithId(HttpMethod.PUT, "update-post")
                            .relWithId(HttpMethod.DELETE, "delete-post")
                            .profile("resources-posts-get")
                        );

    public static Function<Post, ResponseEntity> addLinksForDeletePost =
            post -> ResponseEntity.ok(
                        PostResource.of(post)
                            .selfWithId(HttpMethod.DELETE)
                            .rel(HttpMethod.GET, "post-list")
                            .profile("resources-delete-post")
                        );

    private static ResourceAssembler<Post, PostResource> postPagingResourceAssembler =
            post -> PostResource.of(post)
                        .profile("resource-posts-get")
                        .selfWithId(HttpMethod.GET)
                        .relWithId(HttpMethod.PUT, "update-post")
                        .relWithId(HttpMethod.DELETE, "delete-post");
}
