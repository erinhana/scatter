package com.example.coe.mappings;

import com.example.coe.entities.Todo;
import com.example.coe.models.todos.TodoDetailViewModel;
import com.example.coe.utils.mapper.SourceToTargetMapping;
import org.modelmapper.PropertyMap;

public class TodoToTodoDetailViewModel implements SourceToTargetMapping<Todo, TodoDetailViewModel> {

    @Override
    public PropertyMap<Todo, TodoDetailViewModel> mapFromSourceToTarget() {
        return new PropertyMap<>() {
            protected void configure() {
                map(source.getUser().getId(), destination.getUserId());
            }
        };
    }
}
