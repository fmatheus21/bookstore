package com.fmatheus.app.infra.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fmatheus.app.controller.converter.ObjectConverter;
import com.fmatheus.app.model.entity.Cambium;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CambiumPublisher {

    @Autowired
    private ObjectConverter objectConverter;

    @Autowired
    private Queue queueCambiumList;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendCambiumObject(Cambium cambium) throws JsonProcessingException {
        var json = this.objectConverter.converterJson(cambium);
        this.rabbitTemplate.convertAndSend(this.queueCambiumList.getName(), json);
    }


}
