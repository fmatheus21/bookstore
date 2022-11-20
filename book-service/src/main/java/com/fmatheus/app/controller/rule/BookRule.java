package com.fmatheus.app.controller.rule;

import com.fmatheus.app.controller.constant.ApplicationConstant;
import com.fmatheus.app.controller.converter.BookConverter;
import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.controller.enumerable.CurrencyEnum;
import com.fmatheus.app.controller.resource.proxy.CambiumResourceProxy;
import com.fmatheus.app.model.repository.CambiumRepository;
import com.fmatheus.app.model.service.BookService;
import com.fmatheus.app.rule.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Component
public class BookRule {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookConverter bookConverter;

    @Autowired
    private ResponseMessage responseMessage;

    @Autowired
    private CambiumResourceProxy cambiumResourceProxy;

    @Autowired
    private CambiumRepository cambiumRepository;


    public BookDtoResponse findBook(int id, String currency) {
        var book = this.bookService.findBook(id, currency).orElseThrow(this.responseMessage::errorNotFound);
        var proxy = this.cambiumResourceProxy.convertCurrency(book.getPrice(), book.getCurrency().name(), currency);
        book.setPrice(Objects.requireNonNull(proxy.getBody()).getConvertedValue());
        book.setCurrency(CurrencyEnum.valueOf(proxy.getBody().getToCurrency()));
        return this.bookConverter.converterToResponse(book);
    }

    public BookDtoResponse findBookFallbackAfterRetry(int id, String currency) {
        var book = this.bookService.findBook(id, currency).orElseThrow(this.responseMessage::errorNotFound);
        var cambium = this.cambiumRepository.findByFromCurrencyAndToCurrency(book.getCurrency().name(), currency).orElseThrow(this.responseMessage::errorNotFound);
        var converterValue = convertCurrency(cambium.getConversionFactor(), book.getPrice());
        book.setPrice(converterValue);
        book.setCurrency(CurrencyEnum.valueOf(cambium.getToCurrency()));
        return this.bookConverter.converterToResponse(book);
    }

    private static BigDecimal convertCurrency(BigDecimal factor, BigDecimal amount) {
        return factor.multiply(amount).setScale(2, RoundingMode.CEILING);
    }

}
