package com.unittest.app.integracion;

import com.unittest.app.dominio.modelo.DatosPago;

public interface PagoEnLinea {
    boolean obtenerAprobacionPago(DatosPago pago);
}
