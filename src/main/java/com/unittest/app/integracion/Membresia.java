package com.unittest.app.integracion;

import com.unittest.app.dominio.modelo.Cliente;
import com.unittest.app.dominio.modelo.TipoMembresia;

public interface Membresia {
    TipoMembresia obtenerMembrecia(Cliente cliente);
    boolean membreciaVigente(Cliente cliente);
}
