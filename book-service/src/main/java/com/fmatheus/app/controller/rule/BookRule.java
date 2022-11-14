package com.fmatheus.app.controller.rule;

import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.model.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookRule {

    @Autowired
    private BookService bookService;

    public BookDtoResponse findBook(int id, String currency) {
        return this.bookService.findBook(id, currency);
    }
}
