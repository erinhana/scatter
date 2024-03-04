package com.example.coe.utils.mapper;

import com.example.coe.mappings.BlockerToBlockerViewModel;
import com.example.coe.mappings.ActivityToActivityViewModel;
import com.example.coe.mappings.TodoToTodoViewModel;
import com.example.coe.mappings.UserToUserDetailViewModel;
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
        mapper.addSourceToTargetMapping(new UserToUserDetailViewModel());
        mapper.addSourceToTargetMapping(new TodoToTodoViewModel());
    }


}