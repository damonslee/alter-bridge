package com.yaboong.alterbridge.application.common.hateoas;

import org.springframework.hateoas.Link;
import org.springframework.validation.Errors;

/**
 * Created by yaboong on 2019-09-22
 */
public class ErrorResourceProvider extends AbstractResourceProvider<Errors> {

    private ErrorResourceProvider(Errors errors, Link... links) {
        super(errors, links);
    }

    public static ErrorResourceProvider of(Errors errors) {
        return new ErrorResourceProvider(errors);
    }

}
