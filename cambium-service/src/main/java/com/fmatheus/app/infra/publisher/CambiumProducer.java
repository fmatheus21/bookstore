package com.fmatheus.app.infra.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fmatheus.app.controller.converter.ObjectConverter;
import com.fmatheus.app.controller.util.ApplicationUtil;
import com.fmatheus.app.model.entity.Cambium;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Log4j2
@RequiredArgsConstructor
@Component
public class CambiumProducer {

    @Autowired
    private ObjectConverter objectConverter;

    private final ApplicationUtil application;

    private final KafkaTemplate<String, String> kafkaTemplate;


    public void sendCambiumObject(Cambium cambium) throws JsonProcessingException {
        log.info("Iniciando envio de dados para o servico book-service pelo Kafka");
        var json = this.objectConverter.converterJson(cambium);
        this.kafkaTemplate.send(this.application.getKafkaTopic(), json).addCallback(
                success -> {
                    if (Objects.nonNull(success)) {
                        log.info("Dados enviados ao servico book-service com sucesso.");
                        log.info("Particao {}, Offset {}.", success.getRecordMetadata().partition(), success.getRecordMetadata().offset());
                    }
                },
                error -> log.error("Erro ao enviar dados ao sevico book-service.")
        );
    }


}
