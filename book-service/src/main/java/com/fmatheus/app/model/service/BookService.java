package com.fmatheus.app.model.service;

import com.fmatheus.app.model.entity.Book;

import java.util.Optional;

public interface BookService extends GenericService<Book, Integer> {

    Optional<Book> findBook(int id, String currency);

}
