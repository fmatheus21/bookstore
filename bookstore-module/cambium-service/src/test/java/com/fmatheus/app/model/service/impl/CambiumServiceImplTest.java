package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.repository.CambiumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class CambiumServiceImplTest {

    private static final int ID = 1;
    private static final String FROM_CURRENCY = "USD";
    private static final String TO_CURRENCY = "BRL";

    @InjectMocks // E necessario uma instancia real.
    private CambiumServiceImpl service;

    @Mock
    private CambiumRepository repository;

    private Cambium cambium;

    private Optional<Cambium> optional;

    @BeforeEach
    void setUp() {
        openMocks(this);
        this.start();
    }

    @Test
    void findAll() {
    }

    @Test
    void findById() {
        when(this.repository.findById(anyInt())).thenReturn(this.optional); //Quando o metodo cambiumRepository.findById() for chamado com qualquer parametro inteiro passado, entao retorna optional
        var response = this.service.findById(ID).get();
        assertNotNull(response);
        assertEquals(Cambium.class, response.getClass());
        assertEquals(ID, response.getId());
    }

    @Test
    void save() {
    }

    @Test
    void findByFromCurrencyAndToCurrency() {
    }

    @Test
    void deleteById() {
    }


    private void start() {
        this.cambium = this.loadCambium();
        this.optional = Optional.of(this.loadCambium());
    }

    private Cambium loadCambium() {
        return Cambium.builder()
                .id(ID)
                .fromCurrency(FROM_CURRENCY)
                .toCurrency(TO_CURRENCY)
                .conversionFactor(BigDecimal.valueOf(5.73))
                .build();
    }

}