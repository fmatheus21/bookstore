package com.fmatheus.app.model.repository;

import com.fmatheus.app.model.entity.Cambium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CambiumRepository extends JpaRepository<Cambium, Integer> {
    Optional<Cambium> findByFromCurrencyAndToCurrency(String fromCurrency, String toCurrency);
}
