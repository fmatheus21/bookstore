package com.fmatheus.app.controller.constant;

public class ResourceConstant {

    private ResourceConstant() {
        throw new IllegalStateException(getClass().getName());
    }

    public static final String BOOK_SERVICE = "/book-service";
    public static final String ID = "/{id}";
    public static final String CURRENCY = "/{currency}";
    public static final String PROXY_CAMBIUM_NAME = "${microservice.cambium.name}";
    public static final String PROXY_CAMBIUM_CONVERTER_CAMBIO = "${microservice.cambium.resource.converter-cambium}";
}
