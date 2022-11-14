package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.controller.converter.BookConverter;
import com.fmatheus.app.controller.dto.request.BookDtoRequest;
import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.controller.resource.proxy.CambiumResourceProxy;
import com.fmatheus.app.model.entity.Book;
import com.fmatheus.app.model.repository.BookRepository;
import com.fmatheus.app.model.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    private BookConverter cambiumConverter;

    @Autowired
    private CambiumResourceProxy cambiumResourceProxy;

    @Override
    public List<BookDtoResponse> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<BookDtoResponse> findById(Integer id) {
        var result = this.repository.findById(id);
        if (result.isPresent()) {
            return result.map(this::converterToResponse);
        }
        return Optional.empty();
    }

    @Override
    public BookDtoResponse save(BookDtoResponse bookDtoResponse) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public BookDtoResponse findBook(int id, String currency) {
        var result = this.findById(id);
        if (result.isPresent()) {
            var proxy = this.cambiumResourceProxy.convertCurrency(result.get().getPrice(), result.get().getCurrency(), currency);
            result.get().setPrice(Objects.requireNonNull(proxy.getBody()).getConvertedValue());
            result.get().setCurrency(proxy.getBody().getToCurrency());
            result.get().setEnvironment(proxy.getBody().getEnvironment());
            return result.get();
        }
        return null;
    }


    private BookDtoResponse converterToResponse(Book cambium) {
        return this.cambiumConverter.converterToResponse(cambium);
    }

    private Book converterToRequest(BookDtoRequest request) {
        return this.cambiumConverter.converterToRequest(request);
    }

    private List<BookDtoResponse> converterList(List<Book> list) {
        return list.stream().map(map -> this.cambiumConverter.converterToResponse(map)).toList();
    }
}
