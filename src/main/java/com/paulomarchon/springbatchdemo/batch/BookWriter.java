package com.paulomarchon.springbatchdemo.batch;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import com.paulomarchon.springbatchdemo.repository.BookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookWriter implements ItemWriter{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void write(Chunk chunk) throws Exception {
        log.info("Writing: {}", chunk);
        bookRepository.saveAll(chunk.getItems());
    }
}
