package com.fmatheus.app.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMqConfig {

    @Value("${mq.queues.bookstore-cambium}")
    private String cambium;

    @Bean
    public Queue queueCambiumList() {
        return new Queue(this.cambium, true);
    }


}
