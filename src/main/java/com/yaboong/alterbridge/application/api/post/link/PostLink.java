package com.yaboong.alterbridge.application.api.post.link;

import com.yaboong.alterbridge.application.api.post.controller.PostController;
import com.yaboong.alterbridge.application.api.post.domain.PostResource;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.function.Function;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by yaboong on 2019-09-28
 */
public class PostLink {

    public static PagedResources addLinksForPostPagedList(PagedResourcesAssembler<Post> pagedResourcesAssembler, Page<Post> postPagedList) {
        PagedResources<PostResource> postResources = pagedResourcesAssembler.toResource(postPagedList, postPagingResourceAssembler);
        postResources.add(new Link("/docs/index.html#resources-posts-list").withRel("profile"));
        return postResources;
    }

    public static PostResource addLinksForCreatePost(Post post) {
        return PostResource.of(post)
                .addLink(linkTo(PostController.class).withSelfRel().withType(HttpMethod.POST.name()))
                .addLink(new Link("/docs/index.html#resources-create-post").withRel("profile"))
                .addLink(linkTo(PostController.class).withRel("post-list").withType(HttpMethod.GET.name()));
    }

    public static Function<Post, ResponseEntity> addLinksForUpdatePost =
            post -> ResponseEntity.ok(
                        PostResource.of(post)
                            .addLink(linkTo(PostController.class).slash(post.getPostId()).withSelfRel().withType(HttpMethod.PUT.name()))
                            .addLink(new Link("/docs/index.html#resources-update-post").withRel("profile"))
                            .addLink(linkTo(PostController.class).withRel("post-list").withType(HttpMethod.GET.name()))
                        );

    public static Function<Post, ResponseEntity> addLinksForGetPost =
            post -> ResponseEntity.ok(
                        PostResource.of(post)
                            .addLink(linkTo(PostController.class).slash(post.getPostId()).withSelfRel().withType(HttpMethod.GET.name()))
                            .addLink(linkTo(PostController.class).slash(post.getPostId()).withRel("update-post").withType(HttpMethod.PUT.name()))
                            .addLink(linkTo(PostController.class).slash(post.getPostId()).withRel("delete-post").withType(HttpMethod.DELETE.name()))
                            .addLink(new Link("/docs/index.html#resources-posts-get").withRel("profile"))
                        );

    public static Function<Post, ResponseEntity> addLinksForDeletePost =
            post -> ResponseEntity.ok(
                        PostResource.of(post)
                            .addLink(linkTo(PostController.class).slash(post.getPostId()).withSelfRel().withType(HttpMethod.DELETE.name()))
                            .addLink(linkTo(PostController.class).withRel("post-list").withType(HttpMethod.GET.name()))
                            .addLink(new Link("/docs/index.html#resources-delete-post").withRel("profile"))
                        );

    private static ResourceAssembler<Post, PostResource> postPagingResourceAssembler =
            post -> PostResource.of(post)
                        .addLink(new Link("/docs/index.html#resources-posts-get").withRel("profile"))
                        .addLink(linkTo(PostController.class).slash(post.getPostId()).withSelfRel().withType(HttpMethod.GET.name()))
                        .addLink(linkTo(PostController.class).slash(post.getPostId()).withRel("update-post").withType(HttpMethod.PUT.name()))
                        .addLink(linkTo(PostController.class).slash(post.getPostId()).withRel("delete-post").withType(HttpMethod.DELETE.name()));

}
