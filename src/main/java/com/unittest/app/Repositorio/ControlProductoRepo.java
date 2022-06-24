package com.unittest.app.Repositorio;

import com.unittest.app.modelo.TransaccionCompra;
import com.unittest.app.modelo.Producto;

public interface ControlProductoRepo {
    Producto productoRetirado(Producto producto);
    Producto productoRetornado(Producto producto, TransaccionCompra transaccionCompra);
}
