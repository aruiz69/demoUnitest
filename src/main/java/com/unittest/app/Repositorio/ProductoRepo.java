package com.unittest.app.Repositorio;

import com.unittest.app.modelo.Producto;

import java.util.List;

public interface ProductoRepo {
    Producto obtenerProducto(int productoId);
    List<Producto> obtenerProducto(String productoDesc);
}
