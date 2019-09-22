package com.yaboong.alterbridge.application.common.error;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.validation.Errors;

/**
 * Created by yaboong on 2019-09-22
 */
public class ErrorResource extends Resource<Errors> {
    private ErrorResource(Errors errors, Link... links) {
        super(errors, links);
        add(new Link("/docs/index.html#errors").withRel("profile"));
    }

    public static ErrorResource of(Errors errors) {
        return new ErrorResource(errors);
    }
}
