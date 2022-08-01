package com.asw.test.app.app_test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.asw.test.app.app_test.dto.CuentaDto;
import com.asw.test.app.app_test.exceptions.BancoException;
import com.asw.test.app.app_test.service.CuentaService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Log4j2
public class JunitTest {

	@Autowired
	public CuentaService cuentaService;

	@Test
	@Order(2)
	void consultarSaldo() {
		try {
			BigDecimal respuesta = cuentaService.consultarSaldo(4);
			log.info("respuestaConsultarSaldo:" + respuesta);
			assertNotEquals(null,respuesta);
			assertNotEquals(respuesta, BigDecimal.ZERO);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@Order(4)
	void consultarSaldoVariosAssert() {
		try {
			List<CuentaDto> cuentas = cuentaService.cuentasPorUsuario(1);
			assertNotNull(cuentas);
			assertFalse(cuentas.isEmpty(), "");
			assertEquals(0, cuentas.size(), "Supera el tamaño esperado");
			assertEquals(5, cuentas.get(0).getId(), "El registro no corresponde"); // No se va a realizar pues falla en
																					// la anterior
		} catch (Exception e) {
			e.printStackTrace();
			fail(e);
		}
	}

	@Test
	@Order(5)
	void consultarSaldoAssertAll() {
		try {
			List<CuentaDto> cuentas = cuentaService.cuentasPorUsuario(1);
			assertAll(() -> assertNotNull(cuentas), 
					() -> assertTrue(cuentas.isEmpty(), "no esta vacio"), // va a fallar					// aca
					() -> assertEquals(1, cuentas.size(), "El tamaño es superior al limite") // pero va a hacer esta
																								// ultima validacion
			);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e);
		}
	}

	@Test
	@Order(3)
	void consultarSaldoFalla() {
		try {
			cuentaService.consultarSaldo(4);
			throw new Exception("Respuesta incorrecta");
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@Order(1)
	void retirarDineroAssertThrows() {
		BancoException e = assertThrows(BancoException.class, () -> cuentaService.retirar(5, BigDecimal.TEN));
		log.info(e.getMessage());
		assertEquals(e.getCode(), "101", "El Codigo de excepcion no era el esperado");
	}

	@Test
	@Order(6)
	void retirarDineroAssertThrowsFallido() {
		assertThrows(SQLException.class, () -> cuentaService.retirar(1, BigDecimal.TEN));
		log.info("validacion fallida de excepción");
	}

	@Test
	@Order(7)
	void retirarDineroAssertThrowsTipoFallido() {
		assertThrows(NullPointerException.class, () -> cuentaService.retirar(5, BigDecimal.TEN));
		log.info("validacion fallida de excepción");
	}

}
