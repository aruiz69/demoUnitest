package com.unittest.app.servicio.impl;

import com.unittest.app.Repositorio.ControlPagoRepo;
import com.unittest.app.integracion.Membresia;
import com.unittest.app.integracion.PagoEnLinea;
import com.unittest.app.modelo.*;
import com.unittest.app.modelo.dto.ClienteCuentaDto;
import com.unittest.app.modelo.dto.DatoPagoCuentaDto;
import com.unittest.app.modelo.dto.ProductoCuentaDto;
import com.unittest.app.servicio.Compra;
import com.unittest.app.servicio.reglas.DescuentoMembresia;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraImpl implements Compra {
    @Autowired
    ControlPagoRepo controlPagoRepo;
    @Autowired
    PagoEnLinea pagoEnLinea;
    @Autowired
    Membresia membresiaSrv;
    @Autowired
    List<DescuentoMembresia> descuentoMembresias;

    @Override
    public Pago ventaDeProducto(TransaccionCompra transaccionCompra, Cliente cliente) {
        Pago pago = new Pago();
        pago.setTransaccionCompra(transaccionCompra);
        TipoMembresia tipoMembresia;
        Double subtotal  = transaccionCompra.obtenerSubtotal();
        if (cliente.getMembresia() != null && membresiaSrv.membreciaVigente(cliente)) {
            tipoMembresia = cliente.getMembresia().getTipoMembresia();
            Double descuento = 0.0;
            switch (tipoMembresia){
                case DIAMANTE:
                    descuento = subtotal*0.15;
                    break;
                case ORO:
                    descuento = subtotal*0.10;
                    break;
                case PLATA:
                    descuento = subtotal*0.10;
                    break;

            }
            /*Strategy classes
            descuento = descuentoMembresias.stream()
                    .filter(desc -> desc.obtenerTipo()==tipoMembresia)
                    .findAny().get().obtenerDescuento(subtotal);
             */
            pago.setDescuento(descuento);
        }
        pago.setSubtotal(subtotal);
        pago.setTotal(pago.getSubtotal()- pago.getDescuento());
        return controlPagoRepo.guardarPago(pago);
    }

    @Override
    public Pago cancelacionVenta(Pago pago) {
        return null;
    }

    @Override
    public TransaccionCompra agregarProducto(ProductoCuentaDto productoCuentaDto)
    {
        return productoCuentaDto
                .getTransaccionCompra()
                .agregarProducto(productoCuentaDto.getProducto());
    }

    @Override
    public TransaccionCompra agregarDatosPago(DatoPagoCuentaDto datoPagoCuentaDto) {
        return datoPagoCuentaDto.getTransaccionCompra().agregarDatosPago(datoPagoCuentaDto.getDatosPago());
    }


    @Override
    public TransaccionCompra productoCancelado(TransaccionCompra transaccionCompra, Producto producto) {
        return null;
    }

    @Override
    public TransaccionCompra agregarCliente(ClienteCuentaDto clienteCuentaDto) {
        // Valida con sevicio cliente y obtiene detalle de cliente
        return clienteCuentaDto.getTransaccionCompra().agregarCliente(clienteCuentaDto.getCliente());

    }
}
