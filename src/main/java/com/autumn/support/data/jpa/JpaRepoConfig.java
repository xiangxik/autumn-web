package com.autumn.support.data.jpa;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.autumn", repositoryFactoryBeanClass = EntitiyRepositoryFactoryBean.class)
@EnableJpaAuditing
public class JpaRepoConfig {

}
