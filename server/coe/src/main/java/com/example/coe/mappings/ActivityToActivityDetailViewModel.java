package com.example.coe.mappings;

import com.example.coe.entities.Activity;
import com.example.coe.models.activities.ActivityDetailViewModel;
import com.example.coe.models.activities.ActivityViewModel;
import com.example.coe.utils.mapper.SourceToTargetMapping;
import org.modelmapper.PropertyMap;

public class ActivityToActivityDetailViewModel implements SourceToTargetMapping<Activity, ActivityDetailViewModel> {

    @Override
    public PropertyMap<Activity, ActivityDetailViewModel> mapFromSourceToTarget() {
        return new PropertyMap<>() {
            protected void configure() {
                map(source.getTodo().getId(), destination.getTodoId());
            }
        };
    }
}

