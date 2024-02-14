package com.example.coe.utils.mapper;

import com.example.coe.mappings.BlockerToBlockerViewModel;
import com.example.coe.mappings.ActivityToActivityViewModel;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MappingsConfig {

    private final Mapper mapper;

    @PostConstruct
    public void addMappings() {
        mapper.addSourceToTargetMapping(new BlockerToBlockerViewModel());
        mapper.addSourceToTargetMapping(new ActivityToActivityViewModel());
    }


}