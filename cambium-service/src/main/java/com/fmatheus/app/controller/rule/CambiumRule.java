package com.fmatheus.app.controller.rule;

import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.model.service.CambiumService;
import com.fmatheus.app.rule.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CambiumRule {

    @Autowired
    private CambiumService cambiumService;

    @Autowired
    private ResponseMessage responseMessage;

    public CambiumDtoResponse convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        return this.cambiumService.findByFromCurrencyAndToCurrency(amount, fromCurrency, toCurrency).orElse(null);
    }


}
