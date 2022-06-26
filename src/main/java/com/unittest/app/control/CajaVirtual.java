package com.unittest.app.control;

import com.unittest.app.dominio.modelo.Pago;
import com.unittest.app.dominio.modelo.TransaccionCompra;

public interface CajaVirtual {
    Pago consultarPago(Integer folio);

    Pago procesarPago(TransaccionCompra transaccionCompra);

    String info();
}
