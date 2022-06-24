package com.unittest.app.modelo;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
// Agregado Raiz
public class TransaccionCompra {
    UUID id;
    List<Producto> productoList;
    double subtotal;
    Cliente cliente;
    EstatusCuenta estatusCuenta;
    List<DatosPago> datosPago;

    public TransaccionCompra agregarProducto(Producto producto) {
        productoList.add(producto);
        return this;
    }

    public TransaccionCompra retirarProducto(Producto producto) {
        productoList.remove(producto);
        return this;
    }

    public double obtenerSubtotal() {
        return productoList.stream()
                .mapToDouble(producto -> producto.precio)
                .sum();
    }

    public TransaccionCompra agregarDatosPago(DatosPago pago) {
        datosPago.add(pago);
        return this;
    }

    public TransaccionCompra demoverDatosPago(DatosPago pago) {
        datosPago.remove(pago);
        return this;
    }

    public TransaccionCompra agregarCliente(Cliente cliente) {
        setCliente(cliente);
        return this;
    }
}
