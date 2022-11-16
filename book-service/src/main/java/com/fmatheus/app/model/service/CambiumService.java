package com.fmatheus.app.model.service;

import com.fmatheus.app.model.entity.Cambium;

import java.math.BigDecimal;
import java.util.Optional;

public interface CambiumService extends GenericService<Cambium, Integer> {

    Optional<Cambium> findByFromCurrencyAndToCurrency(BigDecimal amount, String fromCurrency, String toCurrency);
}
