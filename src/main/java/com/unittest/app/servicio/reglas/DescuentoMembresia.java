package com.unittest.app.servicio.reglas;

import com.unittest.app.modelo.TipoMembresia;

public interface DescuentoMembresia {
    Double obtenerDescuento(Double subTotal);
    TipoMembresia obtenerTipo();
}
