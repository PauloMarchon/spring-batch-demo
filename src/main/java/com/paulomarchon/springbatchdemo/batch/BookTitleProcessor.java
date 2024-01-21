package com.paulomarchon.springbatchdemo.batch;

import org.springframework.batch.item.ItemProcessor;

import com.paulomarchon.springbatchdemo.entity.Book;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookTitleProcessor implements ItemProcessor<Book, Book>{

    @Override
    public Book process(Book item) throws Exception {
        log.info("Processing title for {}", item);
        item.setTitle(item.getTitle().toUpperCase());
        return item;
    }
    
}
