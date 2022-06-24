package com.unittest.app.servicio;

import com.unittest.app.modelo.*;
import com.unittest.app.modelo.dto.ClienteCuentaDto;
import com.unittest.app.modelo.dto.DatoPagoCuentaDto;
import com.unittest.app.modelo.dto.ProductoCuentaDto;

public interface Compra {
    Pago ventaDeProducto(TransaccionCompra transaccionCompra, Cliente cliente);
    Pago cancelacionVenta(Pago pago);
    TransaccionCompra productoCancelado(TransaccionCompra transaccionCompra, Producto producto);
    TransaccionCompra agregarCliente(ClienteCuentaDto clienteCuentaDto);
    TransaccionCompra agregarProducto(ProductoCuentaDto productoCuentaDto);
    TransaccionCompra agregarDatosPago(DatoPagoCuentaDto datoPagoCuentaDto);
}
