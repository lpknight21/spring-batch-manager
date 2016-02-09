package com.edo.user.job;

import com.edo.user.model.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisPagingItemReader;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class UserExportJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private static final String OVERRIDDEN_BY_EXPRESSION = null;

    @Bean(name = "exportUserJob")
    public Job exportUsersJob() {
        return jobBuilders.get("exportUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step())
                .end()
                .build();
    }

    @Bean(name = "exportUserStep")
    public Step step() {
        return stepBuilders.get("exportUserStep")
                .<User, User> chunk(5)
                .reader(reader(OVERRIDDEN_BY_EXPRESSION))
                .writer(writer(OVERRIDDEN_BY_EXPRESSION))
                .build();
    }

    @Bean(name = "userExportReader")
    @StepScope
    public MyBatisPagingItemReader<User> reader(@Value("#{jobParameters[lastRunTime]}") String lastRunTime) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("timestamp", lastRunTime);

        MyBatisPagingItemReader<User> reader = new MyBatisPagingItemReader<>();
        reader.setParameterValues(queryParams);
        reader.setSqlSessionFactory(sqlSessionFactory);
        reader.setQueryId("getUsersByTimestamp");
        return reader;
    }

    @Bean(name = "exportUserWriter")
    @StepScope
    public FlatFileItemWriter<User> writer(@Value("#{jobParameters[outputPath]}") String outputPath) {
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource(outputPath));
        writer.setLineAggregator(getAggregator());
        return writer;
    }

    private DelimitedLineAggregator<User> getAggregator() {
        DelimitedLineAggregator<User> aggregator = new DelimitedLineAggregator<>();
        aggregator.setDelimiter("|");
        aggregator.setFieldExtractor(getFieldExtractor());
        return aggregator;
    }

    private BeanWrapperFieldExtractor<User> getFieldExtractor() {
        BeanWrapperFieldExtractor<User> extractor = new BeanWrapperFieldExtractor<>();
        extractor.setNames(new String[] {"lastName", "firstName"});
        return extractor;
    }
}
