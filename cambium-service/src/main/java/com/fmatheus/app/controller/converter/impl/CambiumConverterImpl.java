package com.fmatheus.app.controller.converter.impl;

import com.fmatheus.app.controller.converter.CambiumConverter;
import com.fmatheus.app.controller.dto.request.CambiumDtoRequest;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.model.entity.Cambium;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.fmatheus.app.util.AppUtil;

@Component
public class CambiumConverterImpl implements CambiumConverter {

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Cambium converterToRequest(CambiumDtoRequest cambiumDtoRequest) {
        return null;
    }

    @Override
    public CambiumDtoResponse converterToResponse(Cambium cambium) {
        var converter = this.modelMapper.map(cambium, CambiumDtoResponse.class);
        converter.setFromCurrency(AppUtil.convertAllUppercaseCharacters(converter.getFromCurrency()));
        converter.setToCurrency(AppUtil.convertAllUppercaseCharacters(converter.getToCurrency()));
        return converter;
    }

}
