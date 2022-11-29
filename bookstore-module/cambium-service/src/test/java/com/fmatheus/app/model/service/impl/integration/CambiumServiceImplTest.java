package com.fmatheus.app.model.service.impl.integration;

import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.service.impl.CambiumServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Teste de integração de câmbio")
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class CambiumServiceImplTest {

    private static final String YEN = "JPY";

    @Autowired
    private CambiumServiceImpl cambiumService;

    private Cambium cambium;

    @BeforeEach
    public void beforeEach() {
        this.start();
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#save(Cambium)}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso ao salvar um novo câmbio")
    void saveTest() {
        var actualResult = this.cambiumService.save(this.cambium);
        assertNotNull(actualResult);
        assertEquals(Cambium.class, actualResult.getClass());

    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#save(Cambium)}
     */
    @Test
    @Order(2)
    @DisplayName("Sucesso ao atualizar um câmbio")
    void updateTest() {
        var actualConsult = this.cambiumService.findById(TestConstant.ID);
        assertTrue(actualConsult.isPresent());
        actualConsult.get().setToCurrency(YEN);

        var actualResult = this.cambiumService.save(actualConsult.get());
        assertNotNull(actualResult);
        assertEquals(Cambium.class, actualResult.getClass());

    }


    /**
     * Metodo de teste: {@link CambiumServiceImpl#findAll()}
     */
    @Test
    @Order(3)
    @DisplayName("Sucesso na listagem de câmbios")
    void findAllTest() {
        var actualResult = this.cambiumService.findAll();
        Assertions.assertTrue(actualResult.size() > 0);
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#findById(Integer)}
     */
    @Test
    @Order(4)
    @DisplayName("Sucesso na pesquisa de câmbio por id")
    void findByIdTest() {
        var actualResult = this.cambiumService.findById(TestConstant.ID);
        assertTrue(actualResult.isPresent());
        assertEquals(Cambium.class, actualResult.get().getClass());
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#findByFromCurrencyAndToCurrency(String, String)}
     */
    @Test
    @Order(5)
    @DisplayName("Sucesso na pesquisa de um câmbio por parâmetros")
    void findByFromCurrencyAndToCurrencySuccessTest() {
        var actualResult = this.cambiumService.findByFromCurrencyAndToCurrency(TestConstant.FROM_CURRENCY, YEN);
        assertTrue(actualResult.isPresent());
        var result = actualResult.get();
        assertEquals(Cambium.class, result.getClass());
        assertEquals(TestConstant.CONVERSION_FACTOR, result.getConversionFactor());
    }

    /**
     * Metodo de teste: {@link CambiumServiceImpl#deleteById(Integer)}
     */
    @Test
    @Order(6)
    @DisplayName("Sucesso na exclusão de um câmbio")
    void deleteByIdTest() {
        this.cambiumService.deleteById(TestConstant.ID);
        var actualResult = this.cambiumService.findById(TestConstant.ID);
        assertTrue(actualResult.isEmpty());
    }


    private void start() {
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

