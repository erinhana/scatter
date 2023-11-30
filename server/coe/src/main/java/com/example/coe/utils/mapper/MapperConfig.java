package com.example.coe.utils.mapper;

import org.modelmapper.Conditions;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MapperConfig {

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Mapper mapper() {

        final Mapper mapper = new Mapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());

        return mapper;
    }
}
