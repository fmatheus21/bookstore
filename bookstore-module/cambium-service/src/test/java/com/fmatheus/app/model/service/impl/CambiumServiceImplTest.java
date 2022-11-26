package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.controller.converter.CambiumConverter;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.repository.CambiumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
class CambiumServiceImplTest {

    @InjectMocks
    private CambiumServiceImpl cambiumServiceImpl;

    @Mock
    private CambiumConverter cambiumConverter;

    @Mock
    private CambiumRepository cambiumRepository;

    private Optional<Cambium> optional;

    private Cambium cambium;


    private List<Cambium> listCambium = new ArrayList<>();

    @BeforeEach
    void setUp() {
        openMocks(this);
        this.start();
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#findAll()}
     */
    @Test
    void findAllTest() {
        when(this.cambiumRepository.findAll()).thenReturn(this.listCambium);
        var actualResult = this.cambiumServiceImpl.findAll();
        assertNotNull(actualResult);
        assertEquals(1, actualResult.size());
        assertSame(this.listCambium, actualResult);
        assertFalse(actualResult.isEmpty());
        assertEquals(Cambium.class, actualResult.get(0).getClass());
        verify(this.cambiumRepository).findAll();
    }


    /**
     * Metodo de teste: {@link CambiumServiceImpl#findById(Integer)}
     */
    @Test
    void findByIdTest() {
        when(cambiumRepository.findById(anyInt())).thenReturn(this.optional);
        var actualResult = cambiumServiceImpl.findById(TestConstant.ID);
        assertSame(this.optional, actualResult);
        assertTrue(actualResult.isPresent());
        var result = actualResult.get();
        assertEquals(TestConstant.CONVERSION_FACTOR, result.getConversionFactor());
        assertEquals(convertCurrency(result.getConversionFactor()), result.getConvertedValue());
        assertEquals(Cambium.class, result.getClass());
        verify(cambiumRepository).findById(anyInt());
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#save(Cambium)}
     */
    @Test
    void saveTest() {
        when(this.cambiumRepository.save(any())).thenReturn(this.cambium);
        var actualResult = this.cambiumServiceImpl.save(this.cambium);
        assertNotNull(actualResult);
        assertSame(this.cambium, actualResult);
        assertEquals(TestConstant.CONVERSION_FACTOR, actualResult.getConversionFactor());
        assertEquals(convertCurrency(actualResult.getConversionFactor()), actualResult.getConvertedValue());
        assertEquals(Cambium.class, actualResult.getClass());
        verify(this.cambiumRepository).save(any());
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#deleteById(Integer)}
     */
    @Test
    void deleteByIdTest() {
        doNothing().when(this.cambiumRepository).deleteById(anyInt());
        this.cambiumServiceImpl.deleteById(TestConstant.ID);
        verify(this.cambiumRepository).deleteById(anyInt());
        assertTrue(this.cambiumServiceImpl.findAll().isEmpty());
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#findByFromCurrencyAndToCurrency(String, String)}
     */
    @Test
    void findByFromCurrencyAndToCurrencyTest() {
        when(cambiumRepository.findByFromCurrencyAndToCurrency(anyString(), anyString())).thenReturn(this.optional);
        var actualResult = cambiumServiceImpl.findByFromCurrencyAndToCurrency(TestConstant.FROM_CURRENCY, TestConstant.TO_CURRENCY);
        assertSame(this.optional, actualResult);
        assertTrue(actualResult.isPresent());
        var result = actualResult.get();
        assertEquals(TestConstant.CONVERSION_FACTOR, result.getConversionFactor());
        assertEquals(convertCurrency(result.getConversionFactor()), result.getConvertedValue());
        verify(this.cambiumRepository).findByFromCurrencyAndToCurrency(anyString(), anyString());
    }


    private void start() {
        this.cambium = this.loadCambium();
        this.optional = Optional.of(this.loadCambium());
        this.listCambium = List.of(this.cambium);
    }


    private Cambium loadCambium() {
        return Cambium.builder()
                .id(TestConstant.ID)
                .fromCurrency(TestConstant.FROM_CURRENCY)
                .toCurrency(TestConstant.TO_CURRENCY)
                .conversionFactor(TestConstant.CONVERSION_FACTOR)
                .convertedValue(TestConstant.CONVERTED_VALUE)
                .build();
    }


    private static BigDecimal convertCurrency(BigDecimal factor) {
        return factor.multiply(TestConstant.AMOUNT).setScale(2, RoundingMode.CEILING);
    }
}

