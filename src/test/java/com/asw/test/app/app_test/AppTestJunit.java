package com.asw.test.app.app_test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.asw.test.app.app_test.service.CuentaService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class AppTestJunit {

	@Autowired
	public CuentaService cuentaService;

	@Test
	void prueba() {
			log.info("puede no funcionar");
	}
	
	@Test
	void consultarSaldo() {
			cuentaService.consultarSaldo(4);
	}
	
	@Test
	void consultarSaldoError() {
			throw new RuntimeException();
	}
	

}
