package com.fmatheus.app.controller.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ApplicationUtil {

    @Value("${openapi.application.description}")
    private String openApiDescription;

    @Value("${openapi.application.version}")
    private String openApiVersion;

    @Value("${openapi.application.title}")
    private String openApiTitle;

    @Value("${api.kafka.topic}")
    private String kafkaTopic;

    @Value("${api.messenger}")
    private String messenger;


}
