package com.edo.configuration;

import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public interface InfrastructureConfiguration {

    @Bean
    public abstract DataSource dataSource();
}
