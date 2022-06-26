package com.unittest.app.dominio.servicio.reglas;

import com.unittest.app.dominio.modelo.TipoMembresia;

public interface DescuentoMembresia {
    Double obtenerDescuento(Double subTotal);
    TipoMembresia obtenerTipo();
}
