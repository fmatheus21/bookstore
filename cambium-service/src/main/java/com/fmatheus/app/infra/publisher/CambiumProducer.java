package com.fmatheus.app.infra.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmatheus.app.controller.converter.CambiumConverter;
import com.fmatheus.app.controller.util.ApplicationUtil;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.service.CambiumService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CambiumProducer {

    private final ApplicationUtil application;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final CambiumConverter cambiumConverter;

    private final CambiumService cambiumService;

    public void sendCambiumList(Cambium cambium) throws JsonProcessingException {
        log.info("Iniciando envio de dados para o servico book-service pelo Kafka");
        var json = this.converterJson(cambium);
        this.kafkaTemplate.send(this.application.getKafkaTopic(), json);
        log.info("Envio de dados para o servico book-service pelo Kafka finalizado");
    }

    private String converterJson(Cambium cambium) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var converter = this.cambiumConverter.converterToResponse(cambium);
        return mapper.writeValueAsString(converter);
    }
}
