package com.unittest.app.control.impl;

import com.unittest.app.Repositorio.ProductoRepo;
import com.unittest.app.control.CajaVirtual;
import com.unittest.app.modelo.*;
import com.unittest.app.modelo.dto.ClienteCuentaDto;
import com.unittest.app.modelo.dto.DatoPagoCuentaDto;
import com.unittest.app.modelo.dto.ProductoCuentaDto;
import com.unittest.app.servicio.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-cajavirtual/1.0")
public class CajaVirtualImpl implements CajaVirtual {

    @Autowired
    Compra compra;
    @Autowired
    ProductoRepo productoRepo;

    @Override
    @GetMapping("/producto/{Id}")
    public Producto consultarProducto(@PathVariable(value="Id")Integer productoId) {
        return productoRepo.obtenerProducto(productoId);
    }


    @Override
    @PostMapping("/pago")
    public Pago procesarPago(TransaccionCompra transaccionCompra) {
        return compra.ventaDeProducto(transaccionCompra, transaccionCompra.getCliente());
    }


    @Override
    @PostMapping("/producto/cuenta")
    public TransaccionCompra addProductoCuenta(ProductoCuentaDto productoCuentaDto) {
        return compra.agregarProducto(productoCuentaDto);
    }

    @Override
    public TransaccionCompra removerProductoCuenta(ProductoCuentaDto productoCuentaDto) {
        return productoCuentaDto.getTransaccionCompra().retirarProducto(productoCuentaDto.getProducto());
    }

    @Override
    @PostMapping("/cliente/cuenta")
    public TransaccionCompra agregarInformacionCliente(ClienteCuentaDto clienteCuentaDto) {
        return compra.agregarCliente(clienteCuentaDto);
    }

    @Override
    public TransaccionCompra agregarInformacionPago(DatoPagoCuentaDto datoPagoCuentaDto) {
        return compra.agregarDatosPago(datoPagoCuentaDto);
    }

    @Override
    @GetMapping("/info")
    public String info() {
        return "Demo unitTest <br> Version 1.0-SNAPSHOT <br> Developer : Israel Ruiz";
    }
}
