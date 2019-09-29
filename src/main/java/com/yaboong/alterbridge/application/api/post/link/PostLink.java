package com.yaboong.alterbridge.application.api.post.link;

import com.yaboong.alterbridge.application.api.post.controller.PostController;
import com.yaboong.alterbridge.application.common.hateoas.ResourceProvider;
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
        PagedResources<ResourceProvider> postPagedResources = pagedResourcesAssembler.toResource(postPagedList, postPagingResourceAssembler);
        postPagedResources.add(ResourceProvider.getProfileLink("resources-query-posts"));
        return postPagedResources;
    }

    public static ResourceProvider addLinksForCreatePost(Post post) {
        return ResourceProvider.of(post, PostController.class)
                .self(HttpMethod.POST)
                .profile("resources-create-post")
                .rel(HttpMethod.GET, "post-list");
    }

    public static Function<Post, ResponseEntity> addLinksForUpdatePost =
            post -> ResponseEntity.ok(
                        ResourceProvider.of(post, PostController.class)
                            .selfWithId(HttpMethod.PUT)
                            .profile("resources-update-post")
                            .rel(HttpMethod.GET, "post-list")
                        );

    public static Function<Post, ResponseEntity> addLinksForGetPost =
            post -> ResponseEntity.ok(
                        ResourceProvider.of(post, PostController.class)
                            .selfWithId(HttpMethod.GET)
                            .relWithId(HttpMethod.PUT, "update-post")
                            .relWithId(HttpMethod.DELETE, "delete-post")
                            .profile("resources-posts-get")
                        );

    public static Function<Post, ResponseEntity> addLinksForDeletePost =
            post -> ResponseEntity.ok(
                        ResourceProvider.of(post, PostController.class)
                            .selfWithId(HttpMethod.DELETE)
                            .rel(HttpMethod.GET, "post-list")
                            .profile("resources-delete-post")
                        );

    private static ResourceAssembler<Post, ResourceProvider> postPagingResourceAssembler =
            post -> ResourceProvider.of(post, PostController.class)
                        .profile("resource-posts-get")
                        .selfWithId(HttpMethod.GET)
                        .relWithId(HttpMethod.PUT, "update-post")
                        .relWithId(HttpMethod.DELETE, "delete-post");
}
