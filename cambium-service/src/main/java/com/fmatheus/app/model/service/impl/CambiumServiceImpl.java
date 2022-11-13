package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.controller.constant.ApplicationConstant;
import com.fmatheus.app.controller.converter.CambiumConverter;
import com.fmatheus.app.controller.dto.request.CambiumDtoRequest;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.repository.CambiumRepository;
import com.fmatheus.app.model.service.CambiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class CambiumServiceImpl implements CambiumService {

    @Autowired
    private Environment environment;

    @Autowired
    private CambiumRepository repository;

    @Autowired
    private CambiumConverter cambiumConverter;

    @Override
    public List<CambiumDtoResponse> findAll() {
        return this.converterList(this.repository.findAll());
    }

    @Override
    public Optional<CambiumDtoResponse> findById(Integer id) {
        var result = this.repository.findById(id);
        if (result.isPresent()) {
            return result.map(this::converterToResponse);
        }
        return Optional.empty();
    }

    @Override
    public CambiumDtoResponse save(CambiumDtoResponse cambiumDtoResponse) {
        return null;
    }

    @Override
    public CambiumDtoResponse saveRequest(CambiumDtoRequest request) {
        var commit = this.repository.save(this.converterToRequest(request));
        return this.converterToResponse(commit);
    }


    @Override
    public void deleteById(Integer id) {
        this.repository.deleteById(id);
    }

    @Override
    public Optional<CambiumDtoResponse> findByFromCurrencyAndToCurrency(BigDecimal amount, String fromCurrency, String toCurrency) {
        var result = this.repository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
        if (result.isPresent()) {
            var port = this.environment.getProperty(ApplicationConstant.SERVER_PORT);
            var converterValue = convertCurrency(result.get().getConversionFactor(), amount);
            result.get().setEnvironment(port);
            result.get().setConvertedValue(converterValue);
            return result.map(this::converterToResponse);
        }
        return Optional.empty();
    }

    private CambiumDtoResponse converterToResponse(Cambium cambium) {
        return this.cambiumConverter.converterToResponse(cambium);
    }

    private Cambium converterToRequest(CambiumDtoRequest request) {
        return this.cambiumConverter.converterToRequest(request);
    }

    private List<CambiumDtoResponse> converterList(List<Cambium> list) {
        return list.stream().map(map -> this.cambiumConverter.converterToResponse(map)).toList();
    }

    private static BigDecimal convertCurrency(BigDecimal factor, BigDecimal amount) {
        return factor.multiply(amount).setScale(2, RoundingMode.CEILING);
    }

}
