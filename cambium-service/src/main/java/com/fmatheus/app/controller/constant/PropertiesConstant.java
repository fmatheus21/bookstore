package com.fmatheus.app.controller.constant;


public class PropertiesConstant {

    private PropertiesConstant() {
        throw new IllegalStateException("ValuePropertiesConstant class");
    }

    public static final String OPENAPI_DESCRIPTION = "${openapi.application.description}";
    public static final String OPENAPI_VERSION = "${openapi.application.version}";
    public static final String OPENAPI_TITLE = "${openapi.application.title}";

}
