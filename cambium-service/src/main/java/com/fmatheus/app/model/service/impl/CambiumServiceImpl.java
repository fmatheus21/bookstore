package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.repository.CambiumRepository;
import com.fmatheus.app.model.service.CambiumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CambiumServiceImpl implements CambiumService {


    @Autowired
    private CambiumRepository repository;

    @Override
    public List<Cambium> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Optional<Cambium> findById(Integer id) {
        return this.repository.findById(id);
    }

    @Override
    public Cambium save(Cambium cambium) {
        return this.repository.save(cambium);
    }

    @Override
    public Optional<Cambium> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency) {
        return this.repository.findByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
    }


    @Override
    public void deleteById(Integer id) {
        this.repository.deleteById(id);
    }


}
