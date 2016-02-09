package com.edo.user.configuration;

import com.edo.user.configuration.listener.JobCompletionNotificationListener;
import com.edo.user.model.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
public class UserImportJobConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilders;

    @Autowired
    private StepBuilderFactory stepBuilders;

    @Autowired
    private JobCompletionNotificationListener jobCompletionNotificationListener;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private static final String OVERRIDDEN_BY_EXPRESSION = null;

    // tag::jobstep[]
    @Bean(name = "importUserJob")
    public Job importUserJob() {
        return jobBuilders.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(jobCompletionNotificationListener)
                .flow(step())
                .end()
                .build();
    }

    @Bean(name = "importUserStep")
    public Step step() {
        return stepBuilders.get("importUserStep")
                .<User, User>chunk(10)
                .reader(reader(OVERRIDDEN_BY_EXPRESSION))
                .writer(writer())
                .build();
    }
    // end::jobstep[]

    // tag::readerwriterprocessor[]
    @Bean(name = "importUserReader")
    @StepScope
    public FlatFileItemReader<User> reader(@Value("#{jobParameters[inputPath]}") String inputPath) {
        FlatFileItemReader<User> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(inputPath));
        reader.setLineMapper(new DefaultLineMapper<User>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "firstName", "lastName" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<User>() {{
                setTargetType(User.class);
            }});
        }});
        return reader;
    }

    @Bean(name = "importUserWriter")
    public ItemWriter<User> writer() {
        MyBatisBatchItemWriter<User> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("addUser");
        return writer;
    }
    // end::readerwriterprocessor[]
}
