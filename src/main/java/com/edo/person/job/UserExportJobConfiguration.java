package com.edo.person.job;

import com.edo.configuration.InfrastructureConfiguration;
import com.edo.person.model.Person;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class UserExportJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @Autowired
    private InfrastructureConfiguration infrastructureConfiguration;

    @Bean(name = "userExportReader")
    @StepScope
    public JdbcPagingItemReader<Person> reader(@Value("#{jobParameters[lastRunTime]}") String lastRunTime) throws Exception {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("lastRunTime", lastRunTime);

        final JdbcPagingItemReader reader = new JdbcPagingItemReader<>();
        reader.setQueryProvider(getSqlQueryProvider().getObject());
        reader.setDataSource(infrastructureConfiguration.dataSource());
        reader.setPageSize(10);
        reader.setParameterValues(queryParams);
        reader.setRowMapper(new RowMapper<Person>() {
            @Override
            public Person mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Person(resultSet.getString("first_name"), resultSet.getString("last_name"));
            }
        });
        return reader;
    }

    @Bean(name = "userExportQueryProvider")
    public SqlPagingQueryProviderFactoryBean getSqlQueryProvider() {
        SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(infrastructureConfiguration.dataSource());
        sqlPagingQueryProviderFactoryBean.setSelectClause("select first_name, last_name");
        sqlPagingQueryProviderFactoryBean.setFromClause("from person");
        sqlPagingQueryProviderFactoryBean.setWhereClause("where created_timestamp>=lastRunTime");
        return sqlPagingQueryProviderFactoryBean;
    }
}
