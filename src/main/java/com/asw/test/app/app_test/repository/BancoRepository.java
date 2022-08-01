
package com.asw.test.app.app_test.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asw.test.app.app_test.entities.BancoEntity;

@Repository
public interface BancoRepository extends JpaRepository<BancoEntity, Integer> {
}

