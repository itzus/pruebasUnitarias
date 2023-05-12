package com.asw.test.app.app_test.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asw.test.app.app_test.dto.RetiroRequestDto;
import com.asw.test.app.app_test.exceptions.BancoException;
import com.asw.test.app.app_test.service.CuentaService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("cuenta")
public class CuentaController {

	@Autowired
	private CuentaService cuentaService;

	@ApiOperation(value = "CONSULTAR SALDO", notes = "Permite consultar el saldo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Exito al consultar los saldos de una cuenta"),
			@ApiResponse(code = 404, message = "error al consultar los saldos de la cuenta", response = String.class) })
	@GetMapping(path = "{cuentaId}")
	public BigDecimal getSaldo(@PathVariable Integer cuentaId) {
		return cuentaService.consultarSaldo(cuentaId);
	}

	@ApiOperation(value = "RETIRAR DINERO", notes = "Permite realizar un retiro de un monto a una cuenta")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Exito al realizar un retiro de dinero a una cuenta"),
			@ApiResponse(code = 404, message = "error al retirar, no es posible aplicar el retiro", response = String.class) })
	@PostMapping(path = "/retiro", produces = MediaType.APPLICATION_JSON_VALUE)
	public void retiro(@RequestBody RetiroRequestDto dataRetiro) throws BancoException {
			cuentaService.retirar(dataRetiro.getCuenta(), dataRetiro.getMonto());
	}

}