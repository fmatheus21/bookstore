package com.fmatheus.app.controller.resource;

import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.controller.rule.BookRule;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(ResourceConstant.BOOK_SERVICE)
public class BookResource {

    @Autowired
    private BookRule rule;

    private int attempt = 1;

    @GetMapping(ResourceConstant.ID + ResourceConstant.CURRENCY)
    @Retry(name = "retryApi", fallbackMethod = "fallbackAfterRetry") //Implementacao Resilience4j
    public ResponseEntity<BookDtoResponse> findBook(@PathVariable int id, @PathVariable String currency) {
        log.info("Chmando o metodo [findBook]. Tentativa numero {}. Data: {}.", attempt++, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(this.rule.findBook(id, currency));
    }


    public ResponseEntity<BookDtoResponse> fallbackAfterRetry(Exception ex) {
        return ResponseEntity.status(HttpStatus.OK).body(new BookDtoResponse());
    }


}
