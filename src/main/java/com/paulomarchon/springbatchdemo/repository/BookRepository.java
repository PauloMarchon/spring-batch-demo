package com.paulomarchon.springbatchdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.paulomarchon.springbatchdemo.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long>{
    
}
