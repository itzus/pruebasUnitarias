package com.asw.test.app.app_test.entities;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CUENTA")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CuentaEntity implements Serializable {

	private static final long serialVersionUID = -7109536487212965294L;

	@Id
	@Column(name = "ID")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cue_seq")
//    @SequenceGenerator(name = "cue_seq", sequenceName = "CUE_SEQ", allocationSize = 1)
	private Integer id;

	@Column(name = "TIPO")
	private String tipo;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "BANCO_ID")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private BancoEntity banco;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USUARIO_ID")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private UsuarioEntity usuario;

	@Column(name = "SALDO")
	private BigDecimal saldo;

}