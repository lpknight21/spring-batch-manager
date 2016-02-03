package com.edo.person.job;

import com.edo.configuration.InfrastructureConfiguration;
import com.edo.person.model.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
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

    private static final String OVERRIDDEN_BY_EXPRESSION = null;

    @Bean(name = "userExportReader")
    @StepScope
    public JdbcPagingItemReader<Person> reader(@Value("#{jobParameters[lastRunTime]}") String lastRunTime) throws Exception {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("lastRunTime", lastRunTime);

        final JdbcPagingItemReader<Person> reader = new JdbcPagingItemReader<>();
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

    private SqlPagingQueryProviderFactoryBean getSqlQueryProvider() {
        SqlPagingQueryProviderFactoryBean sqlPagingQueryProviderFactoryBean = new SqlPagingQueryProviderFactoryBean();
        sqlPagingQueryProviderFactoryBean.setDataSource(infrastructureConfiguration.dataSource());
        sqlPagingQueryProviderFactoryBean.setSelectClause("select first_name, last_name");
        sqlPagingQueryProviderFactoryBean.setFromClause("from people");
        sqlPagingQueryProviderFactoryBean.setWhereClause("where created_timestamp >= :lastRunTime");
        sqlPagingQueryProviderFactoryBean.setSortKey("first_name");
        return sqlPagingQueryProviderFactoryBean;
    }

    @Bean(name = "exportUserWriter")
    @StepScope
    public FlatFileItemWriter<Person> writer(@Value("#{jobParameters[outputPath]}") String outputPath) {
        FlatFileItemWriter<Person> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(outputPath));
        writer.setLineAggregator(getAggregator());
        return writer;
    }

    private DelimitedLineAggregator<Person> getAggregator() {
        DelimitedLineAggregator<Person> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter("|");
        aggregator.setFieldExtractor(getFieldExtractor());
        return aggregator;
    }

    private BeanWrapperFieldExtractor<Person> getFieldExtractor() {
        BeanWrapperFieldExtractor<Person> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"lastName", "firstName"});
        return extractor;
    }

    @Bean(name = "exportUserJob")
    public Job exportUsersJob() throws Exception {
        return jobBuilders.get("exportUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step())
                .end()
                .build();
    }

    @Bean(name = "exportUserStep")
    public Step step() throws Exception {
        return stepBuilders.get("exportUserStep")
                .<Person, Person> chunk(5)
                .reader(reader(OVERRIDDEN_BY_EXPRESSION))
                .writer(writer(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }
}
