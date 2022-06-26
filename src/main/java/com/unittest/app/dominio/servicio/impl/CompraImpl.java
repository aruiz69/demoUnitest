package com.unittest.app.dominio.servicio.impl;


import com.unittest.app.dominio.repositorio.ControlPagoRepo;
import com.unittest.app.dominio.modelo.*;
import com.unittest.app.dominio.modelo.dto.CalCuloPagoDto;
import com.unittest.app.dominio.servicio.Compra;
import com.unittest.app.excepcion.CompraPagoNoCompletoException;
import com.unittest.app.integracion.Membresia;
import com.unittest.app.integracion.PagoEnLinea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

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

        final CalCuloPagoDto calCuloPagoDto = new CalCuloPagoDto();
        calCuloPagoDto.setPago(pago);
        DatosPago accumulador = DatosPago.builder().montoPagado(0.0).build();
        transaccionCompra.getDatosPago()
                .forEach(datosPago -> {
                    if (pago.getPorPagar() > 0) {
                        Double subCalculo = accumulador.getMontoPagado() + datosPago.getMontoPagado();
                        if (subCalculo < pago.getPorPagar()) {
                            accumulador.setMontoPagado(subCalculo);
                            calCuloPagoDto.getPagosPorAutorizar().add(datosPago);
                            pago.setPorPagar(pago.getPorPagar() - subCalculo);
                        } else if (datosPago.getMontoPagado() > pago.getPorPagar()) {
                            Double montoReducido = datosPago.getMontoPagado() - pago.getPorPagar();
                            datosPago.setMontoPagado(datosPago.getMontoPagado() - montoReducido);
                            calCuloPagoDto.getPagosPorAutorizar().add(datosPago);
                            pago.setPorPagar(pago.getPorPagar() - datosPago.getMontoPagado());
                        }
                    }
                });

        if(calCuloPagoDto.getPago().getPorPagar() > 0){
            throw new CompraPagoNoCompletoException("Error Pago no completo");
        }

        calCuloPagoDto.getPagosPorAutorizar()
                .stream()
                .map(datoPago -> pagoEnLinea.obtenerAprobacionPago(datoPago))
                .collect(Collectors.toList())
                .stream().filter(result -> result.booleanValue() == Boolean.FALSE)
                .findAny()
                .ifPresent(controlPago -> {
                    throw new IllegalStateException("Pago No Aprobado ");
                });

        calCuloPagoDto.getPago().setEstatusDePago(EstatusDePago.PAGADO);

        return controlPagoRepo.guardarPago(calCuloPagoDto.getPago());
    }


}
