package com.fmatheus.app.model.service;

import com.fmatheus.app.model.entity.Cambium;

import java.util.Optional;

public interface CambiumService extends GenericService<Cambium, Integer> {

    Optional<Cambium> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}
