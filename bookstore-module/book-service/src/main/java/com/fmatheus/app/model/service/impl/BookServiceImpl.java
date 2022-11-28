package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.model.entity.Book;
import com.fmatheus.app.model.repository.BookRepository;
import com.fmatheus.app.model.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {


    @Autowired
    private BookRepository repository;


    @Override
    public List<Book> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public Book save(Book book) {
        return this.repository.save(book);
    }

    @Override
    public void deleteById(Integer id) {
        this.repository.deleteById(id);
    }


}
