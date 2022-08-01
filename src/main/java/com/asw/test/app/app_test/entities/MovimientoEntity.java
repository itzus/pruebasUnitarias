package com.asw.test.app.app_test.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "MOVIMIENTO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoEntity implements Serializable {

	private static final long serialVersionUID = -4114994196292019840L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mov_seq")
	@SequenceGenerator(name = "mov_seq", sequenceName = "MOV_SEQ", allocationSize = 1)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUENTA_ID")
	private CuentaEntity cuenta;

	@Column(name = "FECHA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;

	@Column(name = "SALDO_ANTERIOR")
	private BigDecimal saldoAnterior;

	@Column(name = "SALDO_NUEVO")
	private BigDecimal saldoNuevo;

	@Column(name = "MONTO")
	private BigDecimal monto;

	@Column(name = "TIPO_MOVIMIENTO")
	private String tipoMovimiento;

}