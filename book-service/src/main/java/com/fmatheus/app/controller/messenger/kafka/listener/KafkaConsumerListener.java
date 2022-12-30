package com.fmatheus.app.controller.messenger.kafka.listener;

import com.fmatheus.app.model.entity.Cambium;
import com.fmatheus.app.model.service.CambiumService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
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
    @KafkaConsumerCustomListener
    public void createCambium(
            @Payload Cambium payload,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long timestamp) {
        log.info("Partition: [{}]]", partition);
        log.info("Topic: [{}]", topic);
        log.info("TimeStamp: [{}]", timestamp);
        log.info("Payload: [{}]", payload);
        this.processCambium(payload);
    }


    /**
     * Atualiza ou cria um novo cambio que vem do microservico cambio-service.
     *
     * @param cambium Json que vem do microservico cambio-service atraves do Kafka.
     * @author Fernando Matheus
     */
    @SneakyThrows
    private void processCambium(Cambium cambium) {

        var result = this.cambiumService.findById(cambium.getId()).orElse(null);

        if (Objects.nonNull(result)) {
            if (result.getConversionFactor().compareTo(cambium.getConversionFactor()) != 0 ||
                    !result.getFromCurrency().equalsIgnoreCase(cambium.getFromCurrency()) ||
                    !result.getToCurrency().equalsIgnoreCase(cambium.getToCurrency())) {

                result.setConversionFactor(cambium.getConversionFactor());
                result.setToCurrency(cambium.getToCurrency());
                result.setConvertedValue(cambium.getConvertedValue());
                this.cambiumService.save(result);
                log.info("Cambio [{}/{}] atualizado para o novo valor de [{}]", cambium.getFromCurrency(), cambium.getToCurrency(), cambium.getConversionFactor());

            }
        }

        if (Objects.isNull(result)) {
            var newCambium = Cambium.builder()
                    .id(cambium.getId())
                    .fromCurrency(cambium.getFromCurrency())
                    .toCurrency(cambium.getToCurrency())
                    .conversionFactor(cambium.getConversionFactor())
                    .build();
            this.cambiumService.save(newCambium);
        }


    }


}
