package com.example.coe.mappings;

import com.example.coe.entities.Activity;
import com.example.coe.entities.Todo;
import com.example.coe.models.activities.ActivityViewModel;
import com.example.coe.models.todos.TodoViewModel;
import com.example.coe.utils.mapper.SourceToTargetMapping;
import org.modelmapper.PropertyMap;

public class TodoToTodoViewModel implements SourceToTargetMapping<Todo, TodoViewModel>
{

    @Override
    public PropertyMap<Todo, TodoViewModel> mapFromSourceToTarget()
    {
        return new PropertyMap<>() {
            protected void configure() {
                map(source.getUser().getId(), destination.getUserId());
                map(source.getId(), destination.getId());
            }
        };
    }
}

