package com.fmatheus.app.model.service;

import com.fmatheus.app.controller.dto.request.CambiumDtoRequest;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;

import java.math.BigDecimal;
import java.util.Optional;

public interface CambiumService extends GenericService<CambiumDtoResponse, Integer> {
    CambiumDtoResponse saveRequest(CambiumDtoRequest request);

    Optional<CambiumDtoResponse> findByFromCurrencyAndToCurrency(BigDecimal amount, String fromCurrency, String toCurrency);
}
