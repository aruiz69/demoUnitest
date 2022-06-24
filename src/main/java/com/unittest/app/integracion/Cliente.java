package com.unittest.app.integracion;

import java.util.List;

public interface Cliente {
    com.unittest.app.modelo.Cliente obtenerClientePorId(int clienteId);
    com.unittest.app.modelo.Cliente obtenerClientePorTelefono(int numTelefono);
    List<com.unittest.app.modelo.Cliente> obtenerClientesPorNombreApellida(String nombre, String apellido);
    //Otros
}
