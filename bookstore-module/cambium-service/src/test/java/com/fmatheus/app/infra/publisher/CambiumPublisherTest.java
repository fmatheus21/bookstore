package com.fmatheus.app.infra.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.controller.converter.CambiumConverter;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.service.CambiumService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("Teste de fila do RabbitMQ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {CambiumPublisher.class})
@ExtendWith(SpringExtension.class)
class CambiumPublisherTest {

    @Autowired
    private CambiumPublisher cambiumPublisher;

    @MockBean
    private CambiumConverter cambiumConverter;

    @MockBean
    private CambiumService cambiumService;

    @MockBean
    private Queue queue;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    private List<Cambium> listCambium;

    private CambiumDtoResponse cambiumDtoResponse;

    @BeforeEach
    public void beforeEach() {
        openMocks(this);
        this.start();
    }

    /**
     * Metodo de teste: {@link CambiumPublisher#sendCambiumList()}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso no envio da lista de câmbios para a fila bookstore_cambium do RabbitMQ")
    void sendCambiumListTest() throws JsonProcessingException, AmqpException {
        when(this.cambiumConverter.converterToResponse(any())).thenReturn(this.cambiumDtoResponse);
        when(this.cambiumService.findAll()).thenReturn(this.listCambium);
        when(this.queue.getName()).thenReturn(TestConstant.QUEUE_NAME);
        doNothing().when(this.rabbitTemplate).convertAndSend(anyString(), (Object) any());
        this.cambiumPublisher.sendCambiumList();
    }


    private void start() {
        this.listCambium = List.of(this.loadCambium());
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

