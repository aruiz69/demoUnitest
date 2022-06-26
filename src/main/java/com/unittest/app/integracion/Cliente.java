package com.unittest.app.integracion;

import java.util.List;

public interface Cliente {
    com.unittest.app.dominio.modelo.Cliente obtenerClientePorId(int clienteId);
    com.unittest.app.dominio.modelo.Cliente obtenerClientePorTelefono(int numTelefono);
    List<com.unittest.app.dominio.modelo.Cliente> obtenerClientesPorNombreApellida(String nombre, String apellido);
    //Otros
}
