package com.fmatheus.app.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.model.service.CambiumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CambiumSubscriber {

    @Autowired
    private CambiumService cambiumService;

    @RabbitListener(queues = "${mq.queues.bookstore-cambium}")
    public void receiveCambiumList(@Payload String payload) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        var listCambium = mapper.readValue(payload, new TypeReference<List<CambiumDtoResponse>>() {
        });
        this.updateCambium(listCambium);
    }

    private void updateCambium(List<CambiumDtoResponse> list) {
        list.forEach(object -> {
            var cambium = this.cambiumService.findById(object.getId());
            if (cambium.isPresent() && cambium.get().getConversionFactor().compareTo(object.getConversionFactor()) != 0) {
                log.info("Atualizando o cambio {}/{} para o novo valor de {}", object.getFromCurrency(), object.getToCurrency(), object.getConversionFactor());
                cambium.get().setConversionFactor(object.getConversionFactor());
                this.cambiumService.save(cambium.get());
            }
        });

    }

}
