package com.yaboong.alterbridge.application.common.hateoas;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

/**
 * Created by yaboong on 2019-09-29
 */
public class AbstractResourceProvider<T> extends Resource<T> {

    protected static final String PROFILE_LINK_PREFIX = "/docs/index.html#";

    protected AbstractResourceProvider(T content, Link... links) {
        super(content, links);
    }

    public Resource buildWithProfile(String anchor) {
        add(generateProfile(anchor));
        return this;
    }

    public static Link generateProfile(String anchor) {
        return new Link(PROFILE_LINK_PREFIX + anchor).withRel("profile");
    }

}
