package com.fmatheus.app.controller.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fmatheus.app.model.entity.Cambium;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ObjectConverter {

    @Autowired
    private CambiumConverter cambiumConverter;

    public String converterJson(Cambium cambium) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var converter = this.cambiumConverter.converterToResponse(cambium);
        return mapper.writeValueAsString(converter);
    }

}
