package com.unittest.app.excepcion;

public class PagoNoAutorizado extends IllegalStateException{
    public PagoNoAutorizado(String mensaje) {
        super(mensaje);
    }
}
