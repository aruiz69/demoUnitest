package com.unittest.app.integracion;

import com.unittest.app.modelo.Cliente;

public interface Membresia {
    com.unittest.app.modelo.Membresia obtenerMembrecia(Cliente cliente);
    boolean membreciaVigente(Cliente cliente);
}
