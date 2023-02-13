package com.asw.test.app.app_test.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class GetCuentasResponseDto  implements Serializable{

	private static final long serialVersionUID = -1760676952570458396L;
	private List<CuentaDto> cuentas;

}