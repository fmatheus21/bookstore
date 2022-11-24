package com.fmatheus.app.controller.rule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fmatheus.app.controller.converter.CambiumConverter;
import com.fmatheus.app.controller.dto.request.CambiumDtoRequest;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.infra.publisher.CambiumPublisher;
import com.fmatheus.app.model.service.CambiumService;
import com.fmatheus.app.rule.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class CambiumRule {

    @Autowired
    private ResponseMessage responseMessage;

    @Autowired
    private CambiumService cambiumService;

    @Autowired
    private CambiumPublisher cambiumPublisher;

    @Autowired
    private CambiumConverter cambiumConverter;

    public CambiumDtoResponse convertCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        var cambium = this.cambiumService.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency).orElseThrow(this.responseMessage::errorCambiumNotConverter);
        var converterValue = convertCurrency(cambium.getConversionFactor(), amount);
        cambium.setConvertedValue(converterValue);
        return this.cambiumConverter.converterToResponse(cambium);
    }


    public CambiumDtoResponse update(int id, CambiumDtoRequest request) {
        var cambium = this.cambiumService.findById(id).orElseThrow(this.responseMessage::errorNotFound);
        cambium.setConversionFactor(request.getConversionFactor());
        cambium.setConversionFactor(request.getConversionFactor());
        this.cambiumService.save(cambium);
        this.sendCambiumList();
        var converter = this.cambiumConverter.converterToResponse(cambium);
        converter.setMessage(this.responseMessage.successUpdate());
        return converter;
    }

    private void sendCambiumList() {
        try {
            cambiumPublisher.sendCambiumList();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private static BigDecimal convertCurrency(BigDecimal factor, BigDecimal amount) {
        return factor.multiply(amount).setScale(2, RoundingMode.CEILING);
    }

}
