package com.fmatheus.app.infra.publisher.unit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fmatheus.app.controller.constant.TestConstant;
import com.fmatheus.app.controller.converter.ObjectConverter;
import com.fmatheus.app.infra.publisher.CambiumPublisher;
import com.fmatheus.app.model.entity.Cambium;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@DisplayName("Teste de fila do RabbitMQ")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ContextConfiguration(classes = {CambiumPublisher.class})
@ExtendWith(SpringExtension.class)
class CambiumPublisherTest {

    @MockBean
    private ObjectConverter objectConverter;

    @Autowired
    private CambiumPublisher cambiumPublisher;

    @MockBean
    private Queue queue;

    @MockBean
    private RabbitTemplate rabbitTemplate;


    private Cambium cambium;

    @BeforeEach
    public void beforeEach() {
        openMocks(this);
        this.start();
    }

    /**
     * Teste: {@link CambiumPublisher#sendCambiumObject(Cambium)}
     */
    @Test
    @Order(1)
    @DisplayName("Sucesso no envio de câmbios para a fila bookstore_cambium do RabbitMQ")
    void SendCambiumObjectTest() throws JsonProcessingException, AmqpException {

        when(this.objectConverter.converterJson(any(Cambium.class))).thenReturn("json");
        when(this.queue.getName()).thenReturn("Name");
        doNothing().when(this.rabbitTemplate).convertAndSend(anyString(), any(Object.class));

        this.cambiumPublisher.sendCambiumObject(this.cambium);
        verify(this.objectConverter).converterJson(any(Cambium.class));
        verify(this.queue).getName();
        verify(this.rabbitTemplate).convertAndSend(anyString(), any(Object.class));
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
                .build();
    }

}

