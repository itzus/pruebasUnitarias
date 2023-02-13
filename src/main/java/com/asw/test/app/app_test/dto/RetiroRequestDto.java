package com.asw.test.app.app_test.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RetiroRequestDto {

    private Integer cuenta;
    private BigDecimal monto;

}