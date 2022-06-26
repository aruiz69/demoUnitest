package com.unittest.app.repositorio.jpa.impl;


import com.unittest.app.dominio.repositorio.ControlPagoRepo;
import com.unittest.app.dominio.modelo.Pago;
import org.springframework.stereotype.Repository;

@Repository
public class ControlPagoRepoImplJPA implements ControlPagoRepo {
    @Override
    public Pago guardarPago(Pago pago) {
        return null;
    }

    @Override
    public Pago pagoCancelado(int pagoId) {
        return null;
    }

    @Override
    public Pago consultarPago(Integer folioPago) {
        return null;
    }
}
