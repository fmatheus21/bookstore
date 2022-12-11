package com.fmatheus.app.controller.constant;

public class ResourceConstant {

    private ResourceConstant() {
        throw new IllegalStateException(getClass().getName());
    }

    private static final String CAMBIUM_SERVICE = "/cambium-service";
    public static final String CAMBIUM_CONVERTER = CAMBIUM_SERVICE + "/converter";
    public static final String ID = "/{id}";
    public static final String CONVERTER = "/{amount}/{fromCurrency}/{toCurrency}";

}
