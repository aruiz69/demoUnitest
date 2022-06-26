package com.unittest.app.dominio.servicio.impl;


import com.unittest.app.dominio.modelo.*;
import com.unittest.app.dominio.repositorio.ControlPagoRepo;
import com.unittest.app.dominio.servicio.Compra;
import com.unittest.app.integracion.Membresia;
import com.unittest.app.integracion.PagoEnLinea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompraImpl implements Compra {
    @Autowired
    ControlPagoRepo controlPagoRepo;
    @Autowired
    PagoEnLinea pagoEnLinea;
    @Autowired
    Membresia membresiaSrv;

    @Override
    public Pago ventaDeProductoPagoEnLinea(TransaccionCompra transaccionCompra, Cliente cliente) {
        Pago pago = new Pago();
        pago.setTransaccionCompra(transaccionCompra);
        TipoMembresia tipoMembresia;
        Double subtotal = transaccionCompra.obtenerSubtotal();

        if (cliente != null && membresiaSrv.membreciaVigente(cliente)) {
            tipoMembresia = membresiaSrv.obtenerMembrecia(cliente);

            Double descuento = 0.0;
            switch (tipoMembresia) {
                case DIAMANTE:
                    descuento = subtotal * 0.15;
                    break;
                case ORO:
                    descuento = subtotal * 0.10;
                    break;
                case PLATA:
                    descuento = subtotal * 0.05;
                    break;
            }
            pago.setDescuento(descuento);

        }
        pago.setSubtotal(subtotal);
        pago.setTotal(pago.getSubtotal() - pago.getDescuento());
        pago.setPorPagar(pago.getTotal());
        pago.setEstatusDePago(EstatusDePago.PAGADO);
        return controlPagoRepo.guardarPago(pago);
    }


}
