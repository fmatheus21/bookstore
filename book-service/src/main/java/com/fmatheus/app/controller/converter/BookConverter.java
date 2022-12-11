package com.fmatheus.app.controller.converter;

import com.fmatheus.app.controller.dto.request.BookDtoRequest;
import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.model.entity.Book;

public interface BookConverter extends MapperConverter<Book, BookDtoRequest, BookDtoResponse> {
}
