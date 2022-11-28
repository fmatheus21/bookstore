package com.fmatheus.app.controller.converter.impl;

import com.fmatheus.app.controller.converter.BookConverter;
import com.fmatheus.app.controller.dto.request.BookDtoRequest;
import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.model.entity.Book;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookConverterImpl implements BookConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Book converterToRequest(BookDtoRequest request) {
        return this.modelMapper.map(request, Book.class);
    }

    @Override
    public BookDtoResponse converterToResponse(Book book) {
        var response = this.modelMapper.map(book, BookDtoResponse.class);
        response.setCurrency(book.getCurrency().name());
        return response;
    }

}
