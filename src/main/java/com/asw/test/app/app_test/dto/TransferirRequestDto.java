package com.asw.test.app.app_test.dto;

import java.math.BigDecimal;

public class TransferirRequestDto {

    private Integer cuentaOrigen;
    private Integer cuentaDestino;
    private BigDecimal monto;

    public TransferirRequestDto() {
    }

    public Integer getCuentaOrigen() {
        return cuentaOrigen;
    }

    public void setCuentaOrigen(Integer cuentaOrigen) {
        this.cuentaOrigen = cuentaOrigen;
    }

    public Integer getCuentaDestino() {
        return cuentaDestino;
    }

    public void setCuentaDestino(Integer cuentaDestino) {
        this.cuentaDestino = cuentaDestino;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
}