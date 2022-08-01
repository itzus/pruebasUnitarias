package com.asw.test.app.app_test.dto;

import java.io.Serializable;
import java.util.List;

public class GetCuentasResponseDto  implements Serializable{

	private static final long serialVersionUID = -1760676952570458396L;
	List<CuentaDto> cuentas;

    public GetCuentasResponseDto() {
    }

    public List<CuentaDto> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaDto> cuentas) {
        this.cuentas = cuentas;
    }
}