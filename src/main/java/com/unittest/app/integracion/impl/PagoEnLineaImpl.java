package com.unittest.app.integracion.impl;

import com.unittest.app.integracion.PagoEnLinea;
import com.unittest.app.dominio.modelo.DatosPago;
import org.springframework.stereotype.Service;

@Service
public class PagoEnLineaImpl implements PagoEnLinea {
    @Override
    public boolean obtenerAprobacionPago(DatosPago pago) {
        return false;
    }
}
