package com.fmatheus.app.controller.resource;

import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.controller.rule.BookRule;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
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

    /**
     * @Retry Ao fazer as requisicoes e o microservico externo falhar, faz novas tentativas e no final cai no fallback.
     * @CircuitBreaker Ao fazer as requisicoes e o microservico externo falhar e a maioria das requisicoes falharem, a tentativa de acesso ao microservico e interrompido
     * e cai para o fallback. Apos um tempo e verificado se o microservico esta funcionando, caso positivo, retomas as requisicoes para este microservico.
     * @RateLimiter Determina as taxas de requisicoes aceitas em um determinado periodo de tempo.
     * @Bulkhead Determina a quantidade de requisicoes concorrentes sera permitido.
     */

    @GetMapping(ResourceConstant.ID + ResourceConstant.CURRENCY)
    //@Retry(name = "default", fallbackMethod = "findBookFallbackAfterRetry")
    @CircuitBreaker(name = "default", fallbackMethod = "findBookFallbackAfterRetry")
    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    public ResponseEntity<BookDtoResponse> findBook(@PathVariable int id, @PathVariable String currency) {
        log.info("Chamando o metodo [findBook]. Data: {}.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(this.rule.findBook(id, currency));
    }


    public ResponseEntity<BookDtoResponse> findBookFallbackAfterRetry(@PathVariable int id, @PathVariable String currency, Exception ex) {
        return ResponseEntity.status(HttpStatus.OK).body(this.rule.findBookFallbackAfterRetry(id, currency));
    }


}
