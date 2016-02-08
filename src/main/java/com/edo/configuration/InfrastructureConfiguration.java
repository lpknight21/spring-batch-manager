package com.edo.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

public interface InfrastructureConfiguration {

    @Bean
    DataSource dataSource();

    @Bean
    SqlSessionFactory sqlSessionFactory() throws Exception;
}
