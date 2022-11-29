package com.fmatheus.app.controller.rule.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.controller.converter.CambiumConverter;
import com.fmatheus.app.controller.dto.request.CambiumDtoRequest;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.controller.rule.CambiumRule;
import com.fmatheus.app.enumerable.MessageEnum;
import com.fmatheus.app.exception.BadRequestException;
import com.fmatheus.app.exception.handler.response.MessageResponse;
import com.fmatheus.app.infra.publisher.CambiumPublisher;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.service.CambiumService;
import com.fmatheus.app.rule.ResponseMessage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("Teste das regras de negócio de câmbio")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {CambiumRule.class})
@ExtendWith(SpringExtension.class)
class CambiumRuleTest {

    @Autowired
    private CambiumRule cambiumRule;

    @MockBean
    private ResponseMessage responseMessage;

    @MockBean
    private CambiumService cambiumService;

    @MockBean
    private CambiumPublisher cambiumPublisher;

    @MockBean
    private CambiumConverter cambiumConverter;

    private Optional<Cambium> optional;

    private Cambium cambium;

    private CambiumDtoResponse cambiumDtoResponse;

    private CambiumDtoRequest cambiumDtoRequest;

    private MessageResponse messageResponse;


    @BeforeEach
    public void beforeEach() {
        openMocks(this);
        this.start();
    }


    /**
     * Metodo de teste: {@link CambiumRule#convertCurrency(BigDecimal, String, String)}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso na conversão de câmbio")
    void convertCurrencySuccessTest() {

        when(this.cambiumService.findByFromCurrencyAndToCurrency(anyString(), anyString())).thenReturn(this.optional);
        when(this.cambiumConverter.converterToResponse(any())).thenReturn(this.cambiumDtoResponse);
        assertSame(this.cambiumDtoResponse, this.cambiumRule.convertCurrency(TestConstant.AMOUNT, TestConstant.FROM_CURRENCY, TestConstant.TO_CURRENCY));
        verify(this.cambiumConverter).converterToResponse(any());
        verify(this.cambiumService).findByFromCurrencyAndToCurrency(anyString(), anyString());

        var convertCurrencyValue = this.cambiumRule.convertCurrency(TestConstant.AMOUNT, TestConstant.FROM_CURRENCY, TestConstant.TO_CURRENCY);

        assertSame(this.cambiumDtoResponse, convertCurrencyValue);
        assertEquals(this.cambiumDtoResponse, convertCurrencyValue);
        assertEquals(CambiumDtoResponse.class, convertCurrencyValue.getClass());
    }

    /**
     * Metodo de teste: {@link CambiumRule#convertCurrency(BigDecimal, String, String)}
     */
    @Test
    @Order(2)
    @DisplayName("Exceção na conversão de câmbio")
    void convertCurrencyExceptionTest() {
        when(this.cambiumService.findByFromCurrencyAndToCurrency(anyString(), anyString())).thenThrow(new BadRequestException(MessageEnum.ERROR_CAMBIUM_NOT_CONVERTER));
        try {
            this.cambiumRule.convertCurrency(TestConstant.AMOUNT, TestConstant.FROM_CURRENCY, TestConstant.TO_CURRENCY);
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals(MessageEnum.ERROR_CAMBIUM_NOT_CONVERTER.getMessage(), ex.getMessage());
        }
    }


    /**
     * Metodo de teste: {@link CambiumRule#update(int, CambiumDtoRequest)}
     */
    @Test
    @Order(3)
    @DisplayName("Sucesso na atualização de câmbio")
    void updateSuccessTest() throws JsonProcessingException {

        when(this.cambiumConverter.converterToResponse(any())).thenReturn(this.cambiumDtoResponse);
        doNothing().when(this.cambiumPublisher).sendCambiumList();
        when(this.cambiumService.save(any())).thenReturn(this.cambium);
        when(this.cambiumService.findById(anyInt())).thenReturn(this.optional);
        when(this.responseMessage.successUpdate()).thenReturn(this.messageResponse);

        var actualResult = this.cambiumRule.update(TestConstant.ID, this.cambiumDtoRequest);

        assertSame(this.cambiumDtoResponse, actualResult);
        assertSame(this.messageResponse, actualResult.getMessage());
        assertEquals(this.cambiumDtoResponse, actualResult);
        assertEquals(CambiumDtoResponse.class, actualResult.getClass());
        verify(this.cambiumConverter).converterToResponse(any());
        verify(this.cambiumPublisher).sendCambiumList();
        verify(this.cambiumService).save(any());
        verify(this.cambiumService).findById(anyInt());
        verify(this.responseMessage).successUpdate();

    }

    /**
     * Metodo de teste: {@link CambiumRule#update(int, CambiumDtoRequest)}
     */
    @Test
    @Order(4)
    @DisplayName("Exceção na atualização de câmbio")
    void updateExceptionTest() {
        when(this.cambiumService.findById(anyInt())).thenThrow(new BadRequestException(MessageEnum.ERROR_NOT_FOUND));
        try {
            this.cambiumRule.update(TestConstant.ID, this.cambiumDtoRequest);
        } catch (Exception ex) {
            assertEquals(BadRequestException.class, ex.getClass());
            assertEquals(MessageEnum.ERROR_NOT_FOUND.getMessage(), ex.getMessage());
        }
    }

    private void start() {
        this.messageResponse = new MessageResponse();
        this.cambium = this.loadCambium();
        this.optional = Optional.of(this.loadCambium());
        this.cambiumDtoResponse = this.loadCambiumResponse();
        this.cambiumDtoRequest = this.loadCambiumRequest();
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

    private CambiumDtoRequest loadCambiumRequest() {
        return CambiumDtoRequest.builder()
                .fromCurrency(TestConstant.FROM_CURRENCY)
                .toCurrency(TestConstant.TO_CURRENCY)
                .conversionFactor(TestConstant.CONVERSION_FACTOR)
                .build();
    }
}

