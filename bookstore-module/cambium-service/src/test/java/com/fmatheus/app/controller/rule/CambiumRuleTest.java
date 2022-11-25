package com.fmatheus.app.controller.rule;

import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.controller.converter.CambiumConverter;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.enumerable.MessageEnum;
import com.fmatheus.app.exception.BadRequestException;
import com.fmatheus.app.infra.publisher.CambiumPublisher;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.service.CambiumService;
import com.fmatheus.app.rule.ResponseMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

//@SpringBootTest
@ContextConfiguration(classes = {CambiumRule.class})
@ExtendWith(SpringExtension.class)
class CambiumRuleTest {

    @MockBean
    private CambiumConverter cambiumConverter;

    @MockBean
    private CambiumPublisher cambiumPublisher;

    @Autowired
    private CambiumRule cambiumRule;

    @MockBean
    private CambiumService cambiumService;

    @MockBean
    private ResponseMessage responseMessage;

    private Optional<Cambium> optional;

    private Cambium cambium;

    private CambiumDtoResponse cambiumDtoResponse;

    @BeforeEach
    void setUp() {
        openMocks(this);
        this.start();
    }

    /**
     * Metodo de teste: {@link CambiumRule#convertCurrency(BigDecimal, String, String)}
     */
    @Test
    void convertCurrencySuccessTest() {

        when(this.cambiumService.findByFromCurrencyAndToCurrency(anyString(), anyString())).thenReturn(this.optional);
        when(this.cambiumConverter.converterToResponse(any())).thenReturn(this.cambiumDtoResponse);
        assertSame(this.cambiumDtoResponse, this.cambiumRule.convertCurrency(TestConstant.AMOUNT, TestConstant.FROM_CURRENCY, TestConstant.TO_CURRENCY));
        verify(this.cambiumConverter).converterToResponse(any());
        verify(this.cambiumService).findByFromCurrencyAndToCurrency(anyString(), anyString());

        var convertCurrencyRetReturnValue = this.cambiumRule.convertCurrency(TestConstant.AMOUNT, TestConstant.FROM_CURRENCY, TestConstant.TO_CURRENCY);

        assertEquals(this.cambiumDtoResponse, convertCurrencyRetReturnValue);

    }

    @Test
    void convertCurrencyExceptionTest() {
        when(this.cambiumService.findByFromCurrencyAndToCurrency(anyString(), anyString())).thenThrow(new BadRequestException(MessageEnum.ERROR_CAMBIUM_NOT_CONVERTER));
        try {
            this.cambiumRule.convertCurrency(TestConstant.AMOUNT, TestConstant.FROM_CURRENCY, TestConstant.TO_CURRENCY);
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals("message.error.cambium-not-converter", ex.getMessage());
        }
    }


    private void start() {
        this.cambium = this.loadCambium();
        this.optional = Optional.of(this.loadCambium());
        this.cambiumDtoResponse = this.loadCambiumResponse();
    }


    private Cambium loadCambium() {
        return Cambium.builder()
                .id(TestConstant.ID)
                .fromCurrency(TestConstant.FROM_CURRENCY)
                .toCurrency(TestConstant.TO_CURRENCY)
                .conversionFactor(TestConstant.CONVERSION_FACTOR)
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


}

