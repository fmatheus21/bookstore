package com.fmatheus.app.infra.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fmatheus.app.controller.converter.ObjectConverter;
import com.fmatheus.app.model.entity.Cambium;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {CambiumPublisher.class})
@ExtendWith(SpringExtension.class)
class CambiumPublisherTest {
    @Autowired
    private CambiumPublisher cambiumPublisher;

    @MockBean
    private ObjectConverter objectConverter;

    @MockBean
    private Queue queue;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    /**
     * Method under test: {@link CambiumPublisher#sendCambiumObject(Cambium)}
     */
    @Test
    void testSendCambiumObject() throws JsonProcessingException, AmqpException {
        when(objectConverter.converterJson((Cambium) any())).thenReturn("Converter Json");
        when(queue.getName()).thenReturn("Name");
        doNothing().when(rabbitTemplate).convertAndSend((String) any(), (Object) any());

        Cambium cambium = new Cambium();
        cambium.setConversionFactor(BigDecimal.valueOf(42L));
        cambium.setConvertedValue(BigDecimal.valueOf(42L));
        cambium.setFromCurrency("GBP");
        cambium.setId(1);
        cambium.setToCurrency("GBP");
        cambiumPublisher.sendCambiumObject(cambium);
        verify(objectConverter).converterJson((Cambium) any());
        verify(queue).getName();
        verify(rabbitTemplate).convertAndSend((String) any(), (Object) any());
    }
}

