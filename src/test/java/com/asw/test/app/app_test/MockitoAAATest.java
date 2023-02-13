package com.asw.test.app.app_test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.asw.test.app.app_test.entities.BancoEntity;
import com.asw.test.app.app_test.entities.CuentaEntity;
import com.asw.test.app.app_test.entities.UsuarioEntity;
import com.asw.test.app.app_test.exceptions.BancoException;
import com.asw.test.app.app_test.repository.CuentaRepository;
import com.asw.test.app.app_test.service.CuentaService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class MockitoAAATest {
	@MockBean
	public CuentaRepository cuentaRepository;

	@Autowired
	public CuentaService cuentaService;

	@BeforeEach
	void preparar() {
		CuentaEntity cuenta = CuentaEntity.builder().id(1).tipo("CA").banco(new BancoEntity())
				.usuario(new UsuarioEntity()).saldo(new BigDecimal(102L)).build();
		Page<CuentaEntity> rta = new PageImpl<CuentaEntity>(Arrays.asList(cuenta));
		when(cuentaRepository.findAll(PageRequest.of(0, 5))).thenReturn(rta);
		when(cuentaRepository.findById(argThat(arg -> arg > 2 && arg <= 11))).thenReturn(Optional.of(cuenta));
	}

	@Test
	void findall() {
		CuentaEntity cuenta = CuentaEntity.builder().id(1).tipo("CA").banco(new BancoEntity())
				.usuario(new UsuarioEntity()).saldo(new BigDecimal(102L)).build();
		Page<CuentaEntity> rta = new PageImpl<CuentaEntity>(Arrays.asList(cuenta));
		when(cuentaRepository.findAll(any(Pageable.class))).thenReturn(rta);
		Page<CuentaEntity> resultado=cuentaService.cuentasfindAll(PageRequest.of(0, 5));
		assertNotNull(resultado);
		assertEquals(1,resultado.getSize());
		assertEquals(new BigDecimal(102L),resultado.getContent().get(0).getSaldo());
	}

	@Test
	void consultar() {
		try {
			// ACT
			BigDecimal respuesta = cuentaService.consultarSaldo(1);
			// ASSERT
			log.info("respuestaConsultarSaldo:" + respuesta);
			assertNotEquals(respuesta, BigDecimal.ZERO);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void consultarAny() {
		try {
			// ACT
			BigDecimal respuesta = cuentaService.consultarSaldo(1);
			// ASSERT
			log.info("respuestaConsultarSaldo:" + respuesta);
			assertNotEquals(respuesta, BigDecimal.ZERO);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void consultarArgThat() {
		try {
			// ACT
			BigDecimal respuesta = cuentaService.consultarSaldo(11);
			// ASSERT
			log.info("respuestaConsultarSaldo:" + respuesta);
			assertNotEquals(respuesta, BigDecimal.ZERO);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	void consultarThrow() {
		try {
			// ARRANGE
			when(cuentaRepository.findById(24))
					// ACT
					.thenThrow(new NullPointerException("No existe data"));
			// ASSERT
			Exception e = assertThrows(NullPointerException.class, () -> cuentaService.consultarSaldo(24));
			log.info("error:" + e);
			assertNotEquals(e, null);
		} catch (Exception e) {
			fail(e);
		}
	}

	@Test
	@Order(2)
	void retirarDineroFallido() {
		// ACT
		BancoException e = assertThrows(BancoException.class, () -> cuentaService.retirar(5, BigDecimal.TEN));
		// ASSERT
		verify(cuentaRepository, never()).save(any());
		assertNotEquals(e, null);
	}

	@Test
	@Order(1)
	void retirarDinero() {
		try {
			// ACT
			cuentaService.retirar(4, BigDecimal.TEN);
			BigDecimal respuesta = cuentaService.consultarSaldo(11);
			log.info("SALDO retiro dinero:" + respuesta);
			// ASSERT
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
			// ACT
			cuentaService.retirar(4, BigDecimal.TEN);
			BigDecimal respuesta = cuentaService.consultarSaldo(4);
			// ASSERT
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
			ArgumentCaptor<Integer> cuentaRetiro = ArgumentCaptor.forClass(Integer.class);
			// ACT
			cuentaService.retirarCuentaBanco(BigDecimal.TEN);
			BigDecimal respuesta = cuentaService.consultarSaldo(11);
			// ASSERT
			verify(cuentaRepository, times(2)).findById(cuentaRetiro.capture());
			log.info("cuentaRetiro:" + cuentaRetiro.getValue());
			log.info("SALDO retiro CAPTOR:" + respuesta);
//			assertNOEquals(11, cuentaRetiro.getValue());
		} catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			fail("NO funciono el captor");
		}
	}

}
