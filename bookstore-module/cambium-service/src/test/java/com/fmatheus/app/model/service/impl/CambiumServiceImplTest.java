package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.controller.converter.CambiumConverter;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.repository.CambiumRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

@ContextConfiguration(classes = {CambiumServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CambiumServiceImplTest {


    @MockBean
    private CambiumConverter cambiumConverter;

    @MockBean
    private CambiumRepository cambiumRepository;

    @Autowired
    private CambiumServiceImpl cambiumServiceImpl;

    private Optional<Cambium> optional;

    private Cambium cambium;

    private CambiumDtoResponse cambiumDtoResponse;

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
     * Method under test: {@link CambiumServiceImpl#findAll()}
     */
    @Test
    void testFindAll() {
        ArrayList<Cambium> cambiumList = new ArrayList<>();
        when(cambiumRepository.findAll()).thenReturn(cambiumList);
        List<Cambium> actualFindAllResult = cambiumServiceImpl.findAll();
        assertSame(cambiumList, actualFindAllResult);
        assertTrue(actualFindAllResult.isEmpty());
        verify(cambiumRepository).findAll();
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#findById(Integer)}
     */
    @Test
    void findByIdTest() {
        when(cambiumRepository.findById(anyInt())).thenReturn(this.optional);
        Optional<Cambium> actualResult = cambiumServiceImpl.findById(TestConstant.ID);
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
        this.cambiumDtoResponse = this.loadCambiumResponse();
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

    private CambiumDtoResponse loadCambiumResponse() {
        return CambiumDtoResponse.builder()
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

