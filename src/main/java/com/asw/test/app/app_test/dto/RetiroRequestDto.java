package com.asw.test.app.app_test.dto;

import java.math.BigDecimal;

public class RetiroRequestDto {

    private Integer cuenta;
    private BigDecimal monto;

    public RetiroRequestDto() {
    }

    public Integer getCuenta() {
        return cuenta;
    }

    public void setCuenta(Integer cuenta) {
        this.cuenta = cuenta;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}