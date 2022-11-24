package com.fmatheus.app.config;

import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.repository.CambiumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private CambiumRepository cambiumRepository;

    @Bean
    public void startDataBase() {
        var cambiumOne = Cambium.builder()
                .id(1)
                .fromCurrency("USD")
                .toCurrency("BRL")
                .conversionFactor(BigDecimal.valueOf(5.73))
                .build();

        var cambiumTwo = Cambium.builder()
                .id(2)
                .fromCurrency("USD")
                .toCurrency("EUR")
                .conversionFactor(BigDecimal.valueOf(0.84))
                .build();

        var cambiumThree = Cambium.builder()
                .id(3)
                .fromCurrency("USD")
                .toCurrency("GBP")
                .conversionFactor(BigDecimal.valueOf(0.73))
                .build();

        cambiumRepository.saveAll(List.of(cambiumOne, cambiumTwo, cambiumThree));

    }
}
