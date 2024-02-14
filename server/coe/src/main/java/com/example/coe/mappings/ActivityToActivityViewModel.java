package com.example.coe.mappings;

import com.example.coe.entities.Activity;
import com.example.coe.models.activities.ActivityViewModel;
import com.example.coe.utils.mapper.SourceToTargetMapping;
import org.modelmapper.PropertyMap;

public class ActivityToActivityViewModel implements SourceToTargetMapping<Activity, ActivityViewModel>
{

    @Override
    public PropertyMap<Activity, ActivityViewModel> mapFromSourceToTarget()
    {
        return new PropertyMap<>()
        {
            protected void configure()
            {
                map(source.getTodo().getId(), destination.getTodoId());
            }
        };
    }
}

