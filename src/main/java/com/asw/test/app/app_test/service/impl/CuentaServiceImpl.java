
package com.asw.test.app.app_test.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.asw.test.app.app_test.dto.CuentaDto;
import com.asw.test.app.app_test.entities.CuentaEntity;
import com.asw.test.app.app_test.exceptions.BancoException;
import com.asw.test.app.app_test.repository.CuentaRepository;
import com.asw.test.app.app_test.service.CuentaService;
import com.asw.test.app.app_test.util.Converter;

@Service
public class CuentaServiceImpl implements CuentaService {

	@Autowired
	CuentaRepository cuentaRepository;
	@Autowired
	Converter converter;

	@Override
	public BigDecimal consultarSaldo(Integer cuenta) {
		Optional<CuentaEntity> cuentaOpt = cuentaRepository.findById(cuenta);
		if (cuentaOpt.isPresent()) {
			System.out.println(cuentaOpt.get().getId());
			return cuentaOpt.get().getSaldo();
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public void retirar(Integer cuenta, BigDecimal monto) throws BancoException {
		Optional<CuentaEntity> cuentaOpt = cuentaRepository.findById(cuenta);
		CuentaEntity cuentaEntity = cuentaOpt
				.orElseThrow(() -> new BancoException("100", "No Existe cuenta con id " + cuenta));
		BigDecimal nuevoSaldo = cuentaEntity.getSaldo().subtract(monto);
		if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
			throw new BancoException("101", "Dinero insuficiente");
		}
		cuentaEntity.setSaldo(nuevoSaldo);
		cuentaRepository.save(cuentaEntity);
	}

	@Transactional
	@Override
	public void retirar(BigDecimal monto) throws BancoException {
		Optional<CuentaEntity> cuentaOpt = cuentaRepository.findById(11);
		CuentaEntity cuentaEntity = cuentaOpt
				.orElseThrow(() -> new BancoException("100", "No Existe cuenta con id " + 11));
		BigDecimal nuevoSaldo = cuentaEntity.getSaldo().subtract(monto);
		if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
			throw new BancoException("101", "Dinero insuficiente");
		}
		cuentaEntity.setSaldo(nuevoSaldo);
		cuentaRepository.save(cuentaEntity);
	}

	@Transactional
	@Override
	public void transferir(Integer cuentaOrigen, int cuentaDestino, BigDecimal monto) throws BancoException {
		Optional<CuentaEntity> cuentaOpt = cuentaRepository.findById(cuentaDestino);
		CuentaEntity cuentaEntity = cuentaOpt
				.orElseThrow(() -> new BancoException("100", "No Existe cuenta con id " + cuentaDestino));
		retirar(cuentaOrigen, monto);
		BigDecimal nuevoSaldo = cuentaEntity.getSaldo().add(monto);
		cuentaEntity.setSaldo(nuevoSaldo);
		cuentaRepository.save(cuentaEntity);
	}

	@Override
	public List<CuentaDto> cuentasPorUsuario(Integer usuarioId) {
		Optional<List<CuentaEntity>> cuentasOpt = cuentaRepository.findAllByUsuarioId(usuarioId);
		if (cuentasOpt.isPresent()) {
			List<CuentaEntity> list = cuentasOpt.get();
			List<CuentaDto> cuentas = converter.convert(list, CuentaDto.builder().build());
			return cuentas;
		}
		return null;
	}
}
