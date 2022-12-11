package com.fmatheus.app.controller.converter;

import com.fmatheus.app.controller.dto.request.CambiumDtoRequest;
import com.fmatheus.app.controller.dto.response.CambiumDtoResponse;
import com.fmatheus.app.model.entity.Cambium;

public interface CambiumConverter extends MapperConverter<Cambium, CambiumDtoRequest, CambiumDtoResponse> {
}
