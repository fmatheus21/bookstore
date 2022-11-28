package com.fmatheus.app.controller.resource;

import com.fmatheus.app.constant.HttpStatusConstant;
import com.fmatheus.app.controller.constant.OperationConstant;
import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.controller.rule.BookRule;
import com.fmatheus.app.exception.handler.response.MessageResponse;
import com.fmatheus.app.exception.swagger.BadRequest;
import com.fmatheus.app.exception.swagger.Forbidden;
import com.fmatheus.app.exception.swagger.ServerError;
import com.fmatheus.app.exception.swagger.Unauthorized;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = OperationConstant.TAG_BOOK)
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

    @Operation(summary = OperationConstant.GET, description = OperationConstant.DESCRIPTION_GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusConstant.OK_NUMBER, description = HttpStatusConstant.OK,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = MessageResponse.class))),
            @ApiResponse(responseCode = HttpStatusConstant.BAD_REQUEST_NUMBER, description = HttpStatusConstant.BAD_REQUEST,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadRequest.class))),
            @ApiResponse(responseCode = HttpStatusConstant.UNAUTHORIZED_NUMBER, description = HttpStatusConstant.UNAUTHORIZED,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unauthorized.class))),
            @ApiResponse(responseCode = HttpStatusConstant.FORBIDDEN_NUMBER, description = HttpStatusConstant.FORBIDDEN,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Forbidden.class))),
            @ApiResponse(responseCode = HttpStatusConstant.INTERNAL_SERVER_ERROR_NUMBER, description = HttpStatusConstant.INTERNAL_SERVER_ERROR,
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ServerError.class)))
    })
    //@Retry(name = "default", fallbackMethod = "findBookFallbackAfterRetry")
    @CircuitBreaker(name = "default", fallbackMethod = "findBookFallbackAfterRetry")
    @RateLimiter(name = "default")
    @Bulkhead(name = "default")
    @GetMapping(ResourceConstant.ID + ResourceConstant.CURRENCY)
    public ResponseEntity<BookDtoResponse> findBook(@PathVariable int id, @PathVariable String currency) {
        log.info("Chamando o recurso [findBook]. Data: {}.", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.OK).body(this.rule.findBook(id, currency));
    }


    public ResponseEntity<BookDtoResponse> findBookFallbackAfterRetry(@PathVariable int id, @PathVariable String currency, Exception ex) {
        return ResponseEntity.status(HttpStatus.OK).body(this.rule.findBookFallbackAfterRetry(id, currency));
    }


}
