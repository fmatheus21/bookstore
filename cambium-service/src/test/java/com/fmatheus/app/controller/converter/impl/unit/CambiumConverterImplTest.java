package com.fmatheus.app.controller.converter.impl.unit;

import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.controller.converter.impl.CambiumConverterImpl;
import com.fmatheus.app.controller.dto.request.CambiumDtoRequest;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.model.entity.Cambium;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;


@DisplayName("Teste de conversão de entidade e dto")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {CambiumConverterImpl.class})
@ExtendWith(SpringExtension.class)
class CambiumConverterImplTest {

    @Autowired
    private CambiumConverterImpl cambiumConverterImpl;

    @MockBean
    private ModelMapper modelMapper;

    private CambiumDtoResponse cambiumDtoResponse;

    private CambiumDtoRequest cambiumDtoRequest;

    private Cambium cambium;

    @BeforeEach
    public void beforeEach() {
        openMocks(this);
        this.start();
    }

    /**
     * Metodo de teste: {@link CambiumConverterImpl#converterToRequest(CambiumDtoRequest)}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso na conversão de um dto para uma entidade")
    void converterToRequestTest() {
        when(this.modelMapper.map(any(), any())).thenReturn(this.cambium);
        var actualResult = cambiumConverterImpl.converterToRequest(this.cambiumDtoRequest);
        assertSame(this.cambium, actualResult);
        assertNotNull(actualResult.getToCurrency());
        assertNotNull(actualResult.getFromCurrency());
        assertNotNull(actualResult.getConversionFactor());
        verify(this.modelMapper).map(any(), any());
    }


    /**
     * Metodo de teste: {@link CambiumConverterImpl#converterToResponse(Cambium)}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso na conversão de uma entidade para um dto")
    void converterToResponseTest() {
        when(this.modelMapper.map(any(), any())).thenReturn(this.cambiumDtoResponse);
        var actualResult = this.cambiumConverterImpl.converterToResponse(this.cambium);
        assertSame(this.cambiumDtoResponse, actualResult);
        assertNotNull(actualResult.getToCurrency());
        assertNotNull(actualResult.getFromCurrency());
        assertNotNull(actualResult.getConversionFactor());
        assertNotNull(actualResult.getConvertedValue());
        verify(this.modelMapper).map(any(), any());
    }


    private void start() {
        this.cambium = this.loadCambium();
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

