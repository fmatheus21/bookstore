package com.fmatheus.app.model.service.impl;

import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.repository.CambiumRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("Teste da classe de serviço de câmbio")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {CambiumServiceImpl.class})
@ExtendWith(SpringExtension.class)
class CambiumServiceImplTest {

    @MockBean
    private CambiumRepository cambiumRepository;

    @Autowired
    private CambiumServiceImpl cambiumServiceImpl;

    private List<Cambium> listCambium;

    private Optional<Cambium> optional;

    private Cambium cambium;

    @BeforeEach
    public void beforeEach() {
        openMocks(this);
        this.start();
    }


    /**
     * Metodo de teste: {@link CambiumServiceImpl#findAll()}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso na listagem de câmbios")
    void findAllSuccessTest() {
        when(this.cambiumRepository.findAll()).thenReturn(this.listCambium);
        var actualResult = this.cambiumServiceImpl.findAll();
        assertSame(this.listCambium, actualResult);
        assertFalse(actualResult.isEmpty());
        verify(this.cambiumRepository).findAll();
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#findById(Integer)}
     */
    @Test
    @Order(2)
    @DisplayName("Sucesso na pesquisa de câmbio por id")
    void findByIdSuccessTest() {
        when(this.cambiumRepository.findById(anyInt())).thenReturn(this.optional);
        var actualResult = this.cambiumServiceImpl.findById(1);
        assertSame(this.optional, actualResult);
        assertTrue(actualResult.isPresent());
        var result = actualResult.get();
        assertEquals(TestConstant.CONVERSION_FACTOR, result.getConversionFactor());
        assertEquals(TestConstant.CONVERTED_VALUE, result.getConvertedValue());
        verify(this.cambiumRepository).findById(anyInt());
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#save(Cambium)}
     */
    @Test
    @Order(3)
    @DisplayName("Sucesso ao salvar um novo câmbio")
    void saveSuccessTest() {
        when(this.cambiumRepository.save(any())).thenReturn(this.cambium);
        var actualResult = cambiumServiceImpl.save(this.cambium);
        assertSame(this.cambium, actualResult);
        assertEquals(TestConstant.CONVERSION_FACTOR, actualResult.getConversionFactor());
        assertEquals(TestConstant.CONVERTED_VALUE, actualResult.getConvertedValue());
        assertEquals(Cambium.class, actualResult.getClass());
        verify(this.cambiumRepository).save(any());
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#deleteById(Integer)}
     */
    @Test
    @Order(4)
    @DisplayName("Sucesso na exclusão de um câmbio")
    void deleteByIdSuccessTest() {
        doNothing().when(this.cambiumRepository).deleteById(anyInt());
        this.cambiumServiceImpl.deleteById(TestConstant.ID);
        verify(this.cambiumRepository).deleteById(anyInt());
        assertTrue(this.cambiumServiceImpl.findById(TestConstant.ID).isEmpty());
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#findByFromCurrencyAndToCurrency(String, String)}
     */
    @Test
    @Order(5)
    @DisplayName("Sucesso na pesquisa de um câmbio por parâmetros")
    void findByFromCurrencyAndToCurrency() {
        when(this.cambiumRepository.findByFromCurrencyAndToCurrency(anyString(), anyString())).thenReturn(this.optional);
        var actualResult = this.cambiumServiceImpl.findByFromCurrencyAndToCurrency(TestConstant.FROM_CURRENCY, TestConstant.TO_CURRENCY);
        assertSame(this.optional, actualResult);
        assertTrue(actualResult.isPresent());
        var result = actualResult.get();
        assertEquals(TestConstant.CONVERSION_FACTOR, result.getConversionFactor());
        assertEquals(TestConstant.CONVERTED_VALUE, result.getConvertedValue());
        assertEquals(Cambium.class, result.getClass());
        verify(this.cambiumRepository).findByFromCurrencyAndToCurrency(anyString(), anyString());
    }

    private void start() {
        this.listCambium = List.of(this.loadCambium());
        this.optional = Optional.of(this.loadCambium());
        this.cambium = this.loadCambium();
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
}

