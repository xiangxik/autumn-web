package com.autumn.support.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;

@Configuration
public class JsonConfig {

    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(simpleModule());
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(SerializationFeature.FAIL_ON_SELF_REFERENCES);
        return objectMapper;
    }

    @Bean
    public SimpleModule simpleModule() {
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Page.class, new PageSerializer<>());
        return simpleModule;
    }
}
