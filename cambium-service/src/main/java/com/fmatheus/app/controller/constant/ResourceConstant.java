package com.fmatheus.app.controller.constant;

public class ResourceConstant {

    private ResourceConstant() {
        throw new IllegalStateException(getClass().getName());
    }

    public static final String CAMBIUM_SERVICE = "/cambium-service";
    public static final String CONVERTER_CAMBIUM = "/converter/{amount}/{fromCurrency}/{toCurrency}";

}
