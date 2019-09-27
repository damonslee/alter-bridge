package com.yaboong.alterbridge.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by yaboong on 2019-09-27
 */
public interface Input {
    String TITLE = "dummy post title";
    String CONTENT= "dummy post content";
    String CATEGORY = "GENERAL";
    String NORMAL = "NORMAL";
    String VALID_STRING = "VALID_STRING";
    String EMPTY_STRING = StringUtils.EMPTY;
    String INVALID_CATEGORY = "CATEGORY_NOT_EXISTS";
    String INVALID_STATUS = "STATUS_NOT_EXISTS";
}
