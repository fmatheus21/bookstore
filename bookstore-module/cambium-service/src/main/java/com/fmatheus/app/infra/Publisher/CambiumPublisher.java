package com.fmatheus.app.infra.Publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmatheus.app.controller.converter.CambiumConverter;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.service.CambiumService;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CambiumPublisher {

    @Autowired
    private CambiumConverter cambiumConverter;

    @Autowired
    private CambiumService cambiumService;

    @Autowired
    private Queue queueCambiumList;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void sendCambiumList() throws JsonProcessingException {
        var listCambium = this.cambiumService.findAll();
        var json = this.converterJson(listCambium);
        this.rabbitTemplate.convertAndSend(this.queueCambiumList.getName(), json);
    }


    private String converterJson(List<Cambium> cambiumList) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var converter = cambiumList.stream().map(map -> this.cambiumConverter.converterToResponse(map)).toList();
        return mapper.writeValueAsString(converter);
    }

}
