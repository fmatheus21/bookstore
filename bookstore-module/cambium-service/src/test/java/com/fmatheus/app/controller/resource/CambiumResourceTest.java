package com.fmatheus.app.controller.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.controller.dto.request.CambiumDtoRequest;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.controller.rule.CambiumRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CambiumResourceTest {

    @InjectMocks
    private CambiumResource cambiumResource;

    @Mock
    private CambiumRule cambiumRule;

    private CambiumDtoResponse cambiumDtoResponse;

    private CambiumDtoRequest cambiumDtoRequest;

    private ObjectMapper mapper;

    @BeforeEach
    public void beforeEach() {
        openMocks(this);
        this.start();
    }

    /**
     * Metodo de teste: {@link CambiumResource#convertCurrency(BigDecimal, String, String)}
     */
    @Test
    void convertCurrencyTest() throws Exception {
        when(this.cambiumRule.convertCurrency(any(), anyString(), anyString())).thenReturn(this.cambiumDtoResponse);

        String response = this.mapper.writeValueAsString(this.cambiumDtoResponse);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get(ResourceConstant.CAMBIUM_CONVERTER + ResourceConstant.CONVERTER, TestConstant.AMOUNT, TestConstant.FROM_CURRENCY, TestConstant.TO_CURRENCY);
        MockMvcBuilders.standaloneSetup(this.cambiumResource)
                .build()
                .perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestConstant.CONTENT_TYPE_JSON))
                .andExpect(MockMvcResultMatchers.content().string(response));
    }

    /**
     * Metodo de teste: {@link CambiumResource#update(int, CambiumDtoRequest)}
     */
    @Test
    void updateTest() throws Exception {
        when(this.cambiumRule.update(anyInt(), any())).thenReturn(this.cambiumDtoResponse);

        String request = this.mapper.writeValueAsString(this.cambiumDtoRequest);
        String response = this.mapper.writeValueAsString(this.cambiumDtoResponse);

        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.put(ResourceConstant.CAMBIUM_CONVERTER + ResourceConstant.ID, TestConstant.ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request);
        MockMvcBuilders.standaloneSetup(this.cambiumResource)
                .build()
                .perform(builder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(TestConstant.CONTENT_TYPE_JSON))
                .andExpect(MockMvcResultMatchers.content().string(response));
    }

    private void start() {
        this.mapper = new ObjectMapper();
        this.cambiumDtoResponse = this.loadCambiumResponse();
        this.cambiumDtoRequest = this.loadCambiumRequest();
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

