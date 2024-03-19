package com.example.coe.mappings;

import com.example.coe.entities.User;
import com.example.coe.models.users.UserDetailViewModel;
import com.example.coe.utils.mapper.SourceToTargetMapping;
import org.modelmapper.PropertyMap;

public class UserToUserDetailViewModel implements SourceToTargetMapping<User, UserDetailViewModel> {
    @Override
    public PropertyMap<User, UserDetailViewModel> mapFromSourceToTarget() {
        return new PropertyMap<>() {
            protected void configure() {
                map(source.getEmailAddress(), destination.getEmail());
            }
        };
    }
}
