package com.example.coe.utils.mapper;

import org.modelmapper.PropertyMap;

public interface TargetToSourceMapping<T, U> {

    PropertyMap<T, U> mapFromTargetToSource();
}
