package com.fmatheus.app.controller.resource;

import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.dto.response.BookDtoResponse;
import com.fmatheus.app.controller.rule.BookRule;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping(ResourceConstant.BOOK_SERVICE)
public class BookResource {

    //private Logger logger= LoggerFactory.getLogger(BookResource.class);

    @Autowired
    private BookRule rule;

    /*@GetMapping(ResourceConstant.ID + ResourceConstant.CURRENCY)
    @Retry(name = "backendA", fallbackMethod = "fallbackMethod") //Implementacao Resilience4j
    public ResponseEntity<BookDtoResponse> findBook(@PathVariable int id, @PathVariable String currency) {
        return ResponseEntity.status(HttpStatus.OK).body(this.rule.findBook(id, currency));
    }


    public ResponseEntity<BookDtoResponse> fallbackMethod(Exception ex) {
        return ResponseEntity.status(HttpStatus.OK).body(new BookDtoResponse());
    }*/

    @GetMapping("/test")
    @Retry(name = "default")
    private String testFallback() {
        log.info("Tentando conexao...");
        var response = new RestTemplate().getForEntity("http://localhost:8187/test", String.class);
        return response.getBody();
    }

    public String fallbackMethodTest(Exception ex) {
        return "fallbackMethodTest";
    }

}
