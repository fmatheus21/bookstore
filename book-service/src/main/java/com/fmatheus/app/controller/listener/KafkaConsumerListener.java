package com.fmatheus.app.controller.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.service.CambiumService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@Log4j2
@Component
public class KafkaConsumerListener {

    private final CambiumService cambiumService;


    /**
     * Consumidor Kafka.
     *
     * @param payload   Json que vem do microservico cambio-service atraves do Kafka.
     * @param partition Particao do topico
     * @author Fernando Matheus
     */
    @KafkaListener(topicPartitions = @TopicPartition(topic = "${api.kafka.topic}",
            partitionOffsets = {@PartitionOffset(partition = "0", initialOffset = "0"), @PartitionOffset(partition = "1", initialOffset = "0")}),
            containerFactory = "listenerContainerFactory", groupId = "group-1")
    public void createCambium(@Payload String payload, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("Payload recebido [{}], da particao [{}].", payload, partition);
        this.processCambium(payload);
    }


    /**
     * Atualiza ou cria um novo cambio que vem do microservico cambio-service.
     *
     * @param payload Json que vem do microservico cambio-service atraves do Kafka.
     * @author Fernando Matheus
     */
    @SneakyThrows
    private void processCambium(String payload) {
        var mapper = new ObjectMapper();
        var object = mapper.readValue(payload, CambiumDtoResponse.class);

        var result = this.cambiumService.findById(object.getId()).orElse(null);

        if (Objects.nonNull(result)) {
            if (result.getConversionFactor().compareTo(object.getConversionFactor()) != 0 ||
                    !result.getFromCurrency().equalsIgnoreCase(object.getFromCurrency()) ||
                    !result.getToCurrency().equalsIgnoreCase(object.getToCurrency())) {

                result.setConversionFactor(object.getConversionFactor());
                result.setToCurrency(object.getToCurrency());
                result.setConvertedValue(object.getConvertedValue());
                this.cambiumService.save(result);
                log.info("Cambio [{}/{}] atualizado para o novo valor de [{}]", object.getFromCurrency(), object.getToCurrency(), object.getConversionFactor());

            }
        }

        if (Objects.isNull(result)) {
            var cambium = Cambium.builder()
                    .id(object.getId())
                    .fromCurrency(object.getFromCurrency())
                    .toCurrency(object.getToCurrency())
                    .conversionFactor(object.getConversionFactor())
                    .build();
            this.cambiumService.save(cambium);
        }


    }


}
