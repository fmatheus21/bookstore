package com.fmatheus.app.controller.converter;

public interface MapperConverter<T, S, U> {
    T converterToRequest(S s);

    U converterToResponse(T t);
}
