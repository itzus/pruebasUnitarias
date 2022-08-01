package com.asw.test.app.app_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.asw.test.app.app_test.dto.BancoDto;
import com.asw.test.app.app_test.entities.BancoEntity;
import com.asw.test.app.app_test.entities.CuentaEntity;
import com.asw.test.app.app_test.entities.UsuarioEntity;
import com.asw.test.app.app_test.exceptions.BancoException;
import com.asw.test.app.app_test.repository.CuentaRepository;
import com.asw.test.app.app_test.service.CuentaService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MockitoTest {

	@MockBean
	public CuentaRepository cuentaRepository;

	@Autowired
	public CuentaService cuentaService;

	@Test
	void consultar() {
		try {
			CuentaEntity cuenta = CuentaEntity.builder()
					.id(10)
					.tipo("CA")
					.banco(new BancoEntity())
					.usuario(new UsuarioEntity())
					.saldo(new BigDecimal(102L))
					.build();
			when(cuentaRepository.findById(105)).thenReturn(Optional.of(cuenta));
			BigDecimal respuesta = cuentaService.consultarSaldo(105);
			log.info("respuestaConsultarSaldo:" + respuesta); 
			log.info("Cuenta:" + cuenta.toString());
			assertNotEquals(null, respuesta);
			assertNotEquals( BigDecimal.ZERO, respuesta);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void consultarAny() {
		try {
			CuentaEntity cuenta = CuentaEntity.builder()
					.id(1)
					.tipo("CA")
					.banco(new BancoEntity())
					.usuario(new UsuarioEntity())
					.saldo(new BigDecimal(102L))
					.build();
			when(cuentaRepository.findById(anyInt())).thenReturn(Optional.of(cuenta));
			BigDecimal respuesta = cuentaService.consultarSaldo(2000);
			log.info("respuestaConsultarSaldo:" + respuesta);
			assertNotEquals(respuesta, BigDecimal.ZERO);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void consultarArgThat() {
		try {
			CuentaEntity cuenta = CuentaEntity.builder()
					.id(2)
					.tipo("CA")
					.banco(new BancoEntity())
					.usuario(new UsuarioEntity())
					.saldo(new BigDecimal(100002L))
					.build();
			when(cuentaRepository.findById(argThat(arg -> arg > 0 && arg < 10))).thenReturn(Optional.of(cuenta));
			BigDecimal respuesta = cuentaService.consultarSaldo(7);
			log.info("respuestaConsultarSaldo:" + respuesta);
			assertNotEquals(respuesta, null);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void consultarThrow() {
		try {
			when(cuentaRepository.findById(2))
				.thenThrow(new BancoException("100","No existe data"));
			Exception e = assertThrows(BancoException.class, () -> cuentaService.consultarSaldo(2));
			log.info("error:" + e);
			assertNotEquals(e, null);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@Order(2)
	void retirarDineroFallido() {
		System.out.println("respuestaFallida");
		BancoException e = assertThrows(BancoException.class, () -> cuentaService.retirar(5, BigDecimal.TEN));
		verify(cuentaRepository, never()).save(any());
		verify(cuentaRepository, times(2)).findById(5);
		assertNotEquals(e, null);
	}

	@Test
	@Order(1)
	void retirarDinero() {
		try {
			CuentaEntity cuenta = CuentaEntity.builder()
					.id(1)
					.tipo("CA")
					.banco(new BancoEntity())
					.usuario(new UsuarioEntity())
					.saldo(new BigDecimal(102L))
					.build();
			when(cuentaRepository.findById(anyInt())).thenReturn(Optional.of(cuenta));
			cuentaService.retirar(4, BigDecimal.TEN);
			BigDecimal respuesta = cuentaService.consultarSaldo(4);
			verify(cuentaRepository, times(2)).findById(any());
			verify(cuentaRepository).save(any());
			log.info("respuestaretirarDinero:" + respuesta);
			assertNotEquals(respuesta, BigDecimal.ZERO);
		} catch (Exception e) {
			log.info(e.getMessage());
			fail(e.getMessage());
		}
	}

	@Test
	void retirarDineroVerifyFallido() {
		try {
			CuentaEntity cuenta = CuentaEntity.builder()
					.id(1)
					.tipo("CA")
					.banco(new BancoEntity())
					.usuario(new UsuarioEntity())
					.saldo(new BigDecimal(102L))
					.build();
			when(cuentaRepository.findById(anyInt())).thenReturn(Optional.of(cuenta));
			cuentaService.retirar(4, BigDecimal.TEN);
			BigDecimal respuesta = cuentaService.consultarSaldo(4);
			verify(cuentaRepository, times(0)).findById(any());
			verify(cuentaRepository, times(1)).save(any());
			log.info("respuestaretirarDinero:" + respuesta);
			assertNotEquals(respuesta, BigDecimal.ZERO);
		} catch (Exception e) {
			log.info(e.getMessage());
			fail(e.getMessage());
		}
	}

	@Test
	void retirarDineroCaptor() {
		try {
			CuentaEntity cuenta = CuentaEntity.builder()
					.id(1)
					.tipo("CA")
					.banco(new BancoEntity())
					.usuario(new UsuarioEntity())
					.saldo(new BigDecimal(102L))
					.build();
			when(cuentaRepository.findById(anyInt())).thenReturn(Optional.of(cuenta));
			cuentaService.retirar(BigDecimal.TEN);
			ArgumentCaptor<CuentaEntity> cuentaRetiro = ArgumentCaptor.forClass(CuentaEntity.class);
			verify(cuentaRepository).save(cuentaRetiro.capture());
			log.info("cuentaRetiro:" + cuentaRetiro.getValue().toString());
			log.info("cuentaRetiro:" + new BancoDto().toString());
			assertEquals(cuenta, cuentaRetiro.getValue());
		} catch (Exception e) {
			log.info(e.getMessage());
			fail("NO funciono el captor");
		}
	}

}
