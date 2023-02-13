package com.asw.test.app.app_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asw.test.app.app_test.entities.CuentaEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<CuentaEntity, Integer> {

	Optional<List<CuentaEntity>> findAllByBancoId(Integer bancoId);

	Optional<List<CuentaEntity>> findAllByUsuarioId(Integer usuarioId);
}
