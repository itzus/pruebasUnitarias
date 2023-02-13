package com.asw.test.app.app_test.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BANCO")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlEntity implements Serializable {

	private static final long serialVersionUID = -7281470466797616931L;

	@Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ban_seq")
    @SequenceGenerator(name = "ban_seq", sequenceName = "BAN_SEQ", allocationSize = 1)
    private Integer id;

    @Column(name = "NOMBRE")
    private String nombre;
    
    @Column(name = "ESTADO")
    private String estado;

}