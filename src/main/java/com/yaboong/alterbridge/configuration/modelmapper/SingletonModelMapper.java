package com.yaboong.alterbridge.configuration.modelmapper;

import org.modelmapper.ModelMapper;

/**
 * Created by yaboong on 2019-09-16
 */
public class SingletonModelMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static ModelMapper getInstance() {
        return modelMapper;
    }
}
