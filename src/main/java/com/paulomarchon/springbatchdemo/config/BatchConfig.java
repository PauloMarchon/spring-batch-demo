package com.paulomarchon.springbatchdemo.config;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.paulomarchon.springbatchdemo.batch.BookAuthorProcessor;
import com.paulomarchon.springbatchdemo.batch.BookTasklet;
import com.paulomarchon.springbatchdemo.batch.BookTitleProcessor;
import com.paulomarchon.springbatchdemo.batch.BookWriter;
import com.paulomarchon.springbatchdemo.entity.Book;

@Configuration
public class BatchConfig {
    
    @Bean
    public Job bookReaderJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("bookReaderJob", jobRepository)
        .incrementer(new RunIdIncrementer())
        .start(chunkStep(jobRepository, transactionManager))
        .next(taskletStep(jobRepository, transactionManager))
        .build();
    }

    @Bean
    public Step chunkStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("bookReaderStep", jobRepository).<Book, Book>chunk(10, transactionManager)
        .reader(bookReader())
        .processor(bookProcessor())
        .writer(bookWriter())
        .build();
    }

    @Bean
    @StepScope
    public ItemWriter<Book> bookWriter() {
        return new BookWriter();
    }

    @Bean
    @StepScope
    public ItemProcessor<Book, Book> bookProcessor() {
        CompositeItemProcessor<Book, Book> processor = new CompositeItemProcessor<>();
        processor.setDelegates(List.of(new BookTitleProcessor(), new BookAuthorProcessor()));
        return processor;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Book> bookReader() {
        return new FlatFileItemReaderBuilder<Book>()
                .name("bookReader")
                .resource(new ClassPathResource("book_data.csv"))
                .delimited()
                .names(new String[]{"title", "author", "year_of_publishing"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {{
                    setTargetType(Book.class);
                }})
                .build();
    }

    @Bean
    public Step taskletStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("taskletStep", jobRepository)
                .tasklet(new BookTasklet(), transactionManager)
                .build();
    }
}
