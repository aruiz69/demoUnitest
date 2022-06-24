package com.unittest.app.integracion.impl;

import com.unittest.app.integracion.Membresia;
import com.unittest.app.modelo.Cliente;
import org.springframework.stereotype.Service;

@Service
public class MembreciaImpl implements Membresia {
    @Override
    public com.unittest.app.modelo.Membresia obtenerMembrecia(Cliente cliente) {
        return null;
    }

    @Override
    public boolean membreciaVigente(Cliente cliente) {
        return false;
    }
}
