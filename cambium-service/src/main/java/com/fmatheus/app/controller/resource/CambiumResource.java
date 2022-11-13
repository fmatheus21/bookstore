package com.fmatheus.app.controller.resource;

import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.controller.rule.CambiumRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping(ResourceConstant.CAMBIUM_SERVICE)
public class CambiumResource {

    @Autowired
    private CambiumRule rule;

    @GetMapping(ResourceConstant.CONVERTER_CAMBIUM)
    public ResponseEntity<CambiumDtoResponse> convertCurrency(@PathVariable BigDecimal amount, @PathVariable String fromCurrency, @PathVariable String toCurrency) {
        return ResponseEntity.status(HttpStatus.OK).body(rule.convertCurrency(amount, fromCurrency, toCurrency));
    }

}
