package com.unittest.app.Repositorio;

import com.unittest.app.modelo.Pago;

public interface ControlPagoRepo {
    Pago guardarPago(Pago pago);
    Pago Pagocancelado(int pagoId);

}
