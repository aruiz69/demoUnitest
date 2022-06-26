package com.unittest.app.integracion.impl;

import com.unittest.app.dominio.modelo.TipoMembresia;
import com.unittest.app.integracion.Membresia;
import com.unittest.app.dominio.modelo.Cliente;
import org.springframework.stereotype.Service;

@Service
public class MembreciaImpl implements Membresia {
    @Override
    public TipoMembresia obtenerMembrecia(Cliente cliente) {
        return null;
    }

    @Override
    public boolean membreciaVigente(Cliente cliente) {
        return false;
    }
}
