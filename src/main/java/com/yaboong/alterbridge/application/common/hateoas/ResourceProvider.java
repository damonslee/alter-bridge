package com.yaboong.alterbridge.application.common.hateoas;

import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpMethod;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by yaboong on 2019-09-29
 */
public class ResourceProvider<T extends Identifiable> extends AbstractResourceProvider<T> {

    private final Class<?> controller;

    private final T content;

    private ResourceProvider(T content, Class<?> controller, Link... links) {
        super(content, links);
        this.content = content;
        this.controller = controller;
    }

    public static <T extends Identifiable> ResourceProvider of(T content, Class<?> clazz) {
        return new ResourceProvider<>(content, clazz);
    }

    public ResourceProvider self(HttpMethod httpMethod) {
        add(linkTo(controller).withSelfRel().withType(httpMethod.name()));
        return this;
    }

    public ResourceProvider selfWithId(HttpMethod httpMethod) {
        add(linkTo(controller).slash(this.content.getId()).withSelfRel().withType(httpMethod.name()));
        return this;
    }

    public ResourceProvider rel(HttpMethod httpMethod, String rel) {
        add(linkTo(controller).withRel(rel).withType(httpMethod.name()));
        return this;
    }

    public ResourceProvider relWithId(HttpMethod httpMethod, String rel) {
        add(linkTo(controller).slash(this.content.getId()).withRel(rel).withType(httpMethod.name()));
        return this;
    }

}
