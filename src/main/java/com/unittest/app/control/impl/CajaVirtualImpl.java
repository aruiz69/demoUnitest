package com.unittest.app.control.impl;

import com.unittest.app.control.CajaVirtual;
import com.unittest.app.dominio.repositorio.ControlPagoRepo;
import com.unittest.app.dominio.modelo.Pago;
import com.unittest.app.dominio.modelo.TransaccionCompra;
import com.unittest.app.dominio.servicio.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api-cajavirtual/1.0")
public class CajaVirtualImpl implements CajaVirtual {

    @Autowired
    Compra compra;
    @Autowired
    ControlPagoRepo controlPagoRepo;

    @Override
    @GetMapping("/pago/{folio}")
    public Pago consultarPago(@PathVariable(value = "folio") Integer folio) {
        return controlPagoRepo.consultarPago(folio);
    }

    @Override
    @PostMapping("/pago")
    public Pago procesarPago(TransaccionCompra transaccionCompra) {
        return compra.ventaDeProductoPagoEnLinea(transaccionCompra, transaccionCompra.getCliente());
    }

    @Override
    @GetMapping("/info")
    public String info() {
        return "Demo unitTest <br> Version 1.0-SNAPSHOT <br> Developer : Israel Ruiz";
    }
}
