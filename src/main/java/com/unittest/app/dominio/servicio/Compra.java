package com.unittest.app.dominio.servicio;

import com.unittest.app.dominio.modelo.Cliente;
import com.unittest.app.dominio.modelo.Pago;
import com.unittest.app.dominio.modelo.TransaccionCompra;

public interface Compra {
    Pago ventaDeProductoPagoEnLinea(TransaccionCompra transaccionCompra, Cliente cliente);
    // Otros servicios de dominio ...
}
