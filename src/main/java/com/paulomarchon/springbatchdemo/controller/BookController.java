package com.paulomarchon.springbatchdemo.controller;

import com.paulomarchon.springbatchdemo.entity.Book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.paulomarchon.springbatchdemo.repository.BookRepository;

@RestController
@RequestMapping("/book")
public class BookController {
    
    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAll() {
        return bookRepository.findAll();
    }
}
