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

import org.junit.jupiter.api.DisplayName;
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
	@DisplayName("verificación consulta Saldo de la cuenta 4, para confirmar que no es menor que cero")
	void consultarSaldoCuentaCuatroYVerificarQueSeaMayorCero() {
		try {
			BigDecimal respuesta = cuentaService.consultarSaldo(4);
			log.info("respuestaConsultarSaldo:" + respuesta);
			assertNotEquals(null, respuesta, "La cuenta 4 no existe o no se puede determinar el saldo");
			assertTrue(respuesta.compareTo(BigDecimal.ZERO) > 0, "El saldo de la cuenta no es superior a cero");
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@Order(4)
	void consultarSaldoVariosAssert() {
		try {
			List<CuentaDto> cuentas = cuentaService.cuentasPorUsuario(1);
			assertNotNull(cuentas, "las cuentas llegaron nulas");
			assertFalse(cuentas.isEmpty(), "las cuentas llegaron vacias");
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
			List<CuentaDto> cuentas = cuentaService.cuentasPorUsuario(8);
			assertAll(() -> assertNotNull(cuentas, "las cuentas llegaron nulas"),
					() -> assertFalse(cuentas.isEmpty(), "no esta vacio"), // va a fallar // aca
					() -> assertEquals(1, cuentas.size(), "El tamaño es superior a 1"),
					() -> assertEquals(3, cuentas.size(), "El tamaño es superior a 0") // pero va a hacer esta
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
	@DisplayName("verificación retiro no exitoso por cuenta no existente")
	void retirarDineroAssertThrows() {
		BancoException e = assertThrows(BancoException.class, () -> cuentaService.retirar(4, BigDecimal.TEN));
		log.info(e.getMessage());
		assertEquals(e.getCode(), "100",
				"El Codigo de excepcion no era el esperado: se esperaba error por cuenta no existente y arrojo:"
						+ e.getMessage());
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
