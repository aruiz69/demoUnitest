package com.unittest.app.dominio.repositorio;

import com.unittest.app.dominio.modelo.Pago;

public interface ControlPagoRepo {
    Pago guardarPago(Pago pago);
    Pago pagoCancelado(int pagoId);
    Pago consultarPago(Integer folioPago);

}
