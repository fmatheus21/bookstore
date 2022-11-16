package com.fmatheus.app.model.service;

import com.fmatheus.app.controller.dto.response.BookDtoResponse;

public interface BookService extends GenericService<BookDtoResponse, Integer> {

    BookDtoResponse findBook(int id, String currency);

    BookDtoResponse findBookFallbackAfterRetry(int id, String currency);

}
