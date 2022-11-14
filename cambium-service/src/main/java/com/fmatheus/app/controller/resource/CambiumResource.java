package com.fmatheus.app.controller.resource;

import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.messages.ResponseMessages;
import com.fmatheus.app.controller.rule.CambiumRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Objects;

@RestController
@RequestMapping(ResourceConstant.CAMBIUM_SERVICE)
public class CambiumResource {

    @Autowired
    private CambiumRule rule;

    @GetMapping(ResourceConstant.CONVERTER_CAMBIUM)
    public ResponseEntity<?> convertCurrency(@PathVariable BigDecimal amount, @PathVariable String fromCurrency, @PathVariable String toCurrency) {
        var response = rule.convertCurrency(amount, fromCurrency, toCurrency);
        return Objects.nonNull(response) ? ResponseEntity.status(HttpStatus.OK).body(rule.convertCurrency(amount, fromCurrency, toCurrency)) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessages(HttpStatus.BAD_REQUEST, "Câmbio não encontrado."));
    }

}
