package com.example.coe.mappings;

import com.example.coe.entities.Blocker;
import com.example.coe.models.blockers.BlockerViewModel;
import com.example.coe.utils.mapper.SourceToTargetMapping;
import org.modelmapper.PropertyMap;

public class BlockerToBlockerViewModel implements SourceToTargetMapping<Blocker, BlockerViewModel> {

    @Override
    public PropertyMap<Blocker, BlockerViewModel> mapFromSourceToTarget() {
        return new PropertyMap<>() {
            protected void configure() {
                map(source.getUser().getId(), destination.getUserId());
                map(source.getBlockerType().getId(), destination.getBlockerTypeId());
            }
        };
    }
}