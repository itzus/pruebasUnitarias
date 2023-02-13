package com.asw.test.app.app_test;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.asw.test.app.app_test.service.CuentaService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class PruebaInicialContextoErroresJunitTest {

	@Autowired
	public CuentaService cuentaService;

	@Test
	void pruebaExitosaParaJunitPeroNoParaElNegocio() {
		cuentaService.consultarSaldo(4);
		fail("la consulta de saldo no funciono");
	}

	@Test
	void pruebaSinContexto() {
		log.info("puede no funcionar");
	}

	@Test
	void pruebaJunitMostrarComoSeMideUnaExcepcionNoControlada() {
		throw new RuntimeException();
	}

}
