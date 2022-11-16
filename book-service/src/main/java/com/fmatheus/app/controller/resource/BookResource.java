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

    @GetMapping(ResourceConstant.ID + ResourceConstant.CURRENCY)
    @Retry(name = "retryApi", fallbackMethod = "findBookFallbackAfterRetry")
    public ResponseEntity<BookDtoResponse> findBook(@PathVariable int id, @PathVariable String currency) {
        log.info("Chamando o metodo [findBook]. Data: {}.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(this.rule.findBook(id, currency));
    }


    public ResponseEntity<BookDtoResponse> findBookFallbackAfterRetry(@PathVariable int id, @PathVariable String currency, Exception ex) {
        return ResponseEntity.status(HttpStatus.OK).body(this.rule.findBookFallbackAfterRetry(id, currency));
    }


}
