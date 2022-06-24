package com.unittest.app.Repositorio.impl;

import com.unittest.app.Repositorio.ControlPagoRepo;
import com.unittest.app.modelo.Pago;
import org.springframework.stereotype.Repository;

@Repository
public class ControlPagoRepoImpl implements ControlPagoRepo {
    @Override
    public Pago guardarPago(Pago pago) {
        return null;
    }

    @Override
    public Pago Pagocancelado(int pagoId) {
        return null;
    }
}
