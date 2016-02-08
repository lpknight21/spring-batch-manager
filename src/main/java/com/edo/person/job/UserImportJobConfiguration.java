package com.edo.person.job;

import com.edo.person.model.Person;
import com.edo.person.processor.PersonItemProcessor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
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
                .<Person, Person>chunk(10)
                .reader(reader(OVERRIDDEN_BY_EXPRESSION))
                .processor(processor())
                .writer(writer())
                .build();
    }
    // end::jobstep[]

    // tag::readerwriterprocessor[]
    @Bean(name = "importUserReader")
    @StepScope
    public FlatFileItemReader<Person> reader(@Value("#{jobParameters[inputPath]}") String inputPath) {
        FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(inputPath));
        reader.setLineMapper(new DefaultLineMapper<Person>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames(new String[] { "firstName", "lastName" });
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Person>() {{
                setTargetType(Person.class);
            }});
        }});
        return reader;
    }

    @Bean(name = "importUserProcessor")
    public ItemProcessor<Person, Person> processor() {
        return new PersonItemProcessor();
    }

    @Bean(name = "importUserWriter")
    public ItemWriter<Person> writer() {
        MyBatisBatchItemWriter<Person> writer = new MyBatisBatchItemWriter<>();
        writer.setSqlSessionFactory(sqlSessionFactory);
        writer.setStatementId("addPerson");
        return writer;
    }
    // end::readerwriterprocessor[]
}
