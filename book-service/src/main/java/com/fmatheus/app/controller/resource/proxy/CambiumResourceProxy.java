package com.fmatheus.app.controller.resource.proxy;

import com.fmatheus.app.controller.constant.ResourceConstant;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@FeignClient(value = ResourceConstant.PROXY_CAMBIUM_NAME, configuration = {CustomErrorDecoder.class})
public interface CambiumResourceProxy {


    @GetMapping(ResourceConstant.PROXY_CAMBIUM_CONVERTER_CAMBIO)
    ResponseEntity<CambiumDtoResponse> convertCurrency(@PathVariable BigDecimal amount, @PathVariable String fromCurrency, @PathVariable String toCurrency);
}

