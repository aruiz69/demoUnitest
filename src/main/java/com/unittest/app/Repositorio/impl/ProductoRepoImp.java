package com.unittest.app.Repositorio.impl;

import com.unittest.app.Repositorio.ProductoRepo;
import com.unittest.app.modelo.Producto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductoRepoImp implements ProductoRepo {
    @Override
    public Producto obtenerProducto(int productoId) {
        return null;
    }

    @Override
    public List<Producto> obtenerProducto(String productoDesc) {
        return null;
    }
}
