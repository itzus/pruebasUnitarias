package com.asw.test.app.app_test.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDto implements Serializable{

	private static final long serialVersionUID = 8996069433917187424L;
	private Integer id;
	private String tipo;
	private BancoDto banco;
//	private UsuarioDto usuario;
	private BigDecimal saldo;

}