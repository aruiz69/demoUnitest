package com.unittest.app.servicio.reglas;

import com.unittest.app.modelo.TipoMembresia;
import org.springframework.stereotype.Component;

@Component
public class Plata implements DescuentoMembresia{
    @Override
    public Double obtenerDescuento(Double subTotal) {
        return subTotal*0.15;
    }

    @Override
    public TipoMembresia obtenerTipo() {
        return TipoMembresia.PLATA;
    }
}
