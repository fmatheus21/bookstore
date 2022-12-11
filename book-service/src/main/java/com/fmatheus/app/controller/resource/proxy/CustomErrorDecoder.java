package com.fmatheus.app.controller.resource.proxy;

import com.fmatheus.app.rule.ResponseMessage;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomErrorDecoder implements ErrorDecoder {

    @Autowired
    private ResponseMessage responseMessage;

    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 -> responseMessage.errorCambiumNotConverter();
            default -> new Exception("Exceção ao obter detalhes do produto");
        };
    }
}
