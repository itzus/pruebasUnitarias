package com.asw.test.app.app_test.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.asw.test.app.app_test.dto.GetCuentasResponseDto;
import com.asw.test.app.app_test.dto.RetiroRequestDto;
import com.asw.test.app.app_test.dto.TransferirRequestDto;
import com.asw.test.app.app_test.exceptions.BancoException;
import com.asw.test.app.app_test.service.CuentaService;

@RequestMapping("cuenta")
@RestController
public class CuentaController {

    @Autowired
    private CuentaService cuentaService;

    @GetMapping(path = "{cuentaId}")
    public ResponseEntity<BigDecimal> getSaldo(@PathVariable Integer cuentaId) {
        BigDecimal saldo = cuentaService.consultarSaldo(cuentaId);
        return ResponseEntity.ok(saldo);
    }
    
    @PostMapping(path = "/retiro", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retiro(@RequestBody RetiroRequestDto dataRetiro) {
        String msg = "Retiro OK";
        try {
            cuentaService.retirar(dataRetiro.getCuenta(), dataRetiro.getMonto());
        } catch (BancoException be) {
            msg = be.getMessage();
        }
        return ResponseEntity.ok(msg);
    }

    @PostMapping(path = "/transferir", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> transferir(@RequestBody TransferirRequestDto dataTransferencia) {
        String msg = "Transferencia OK";
        try {
            cuentaService.transferir(dataTransferencia.getCuentaOrigen(), dataTransferencia.getCuentaDestino(), dataTransferencia.getMonto());
        } catch (BancoException be) {
            msg = be.getMessage();
        }
        return ResponseEntity.ok(msg);
    }

    @GetMapping(path = "/cuentas/{usuarioId}")
    public ResponseEntity<GetCuentasResponseDto> getCuentas(@PathVariable Integer usuarioId) {
        GetCuentasResponseDto cuentas = new GetCuentasResponseDto();
        cuentas.setCuentas(cuentaService.cuentasPorUsuario(usuarioId));
        return new ResponseEntity(cuentas, HttpStatus.OK);
    }


}