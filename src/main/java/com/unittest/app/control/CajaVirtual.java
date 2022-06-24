package com.unittest.app.control;

import com.unittest.app.modelo.*;
import com.unittest.app.modelo.dto.ClienteCuentaDto;
import com.unittest.app.modelo.dto.DatoPagoCuentaDto;
import com.unittest.app.modelo.dto.ProductoCuentaDto;

public interface CajaVirtual {
    Producto consultarProducto(Integer productoId);
    Pago procesarPago(TransaccionCompra transaccionCompra);
    TransaccionCompra agregarInformacionCliente(ClienteCuentaDto clienteCuentaDto);
    TransaccionCompra agregarInformacionPago(DatoPagoCuentaDto datoPagoCuenta);
    TransaccionCompra addProductoCuenta(ProductoCuentaDto productoCuentaDto);
    TransaccionCompra removerProductoCuenta(ProductoCuentaDto productoCuentaDto);
    String info();
}
