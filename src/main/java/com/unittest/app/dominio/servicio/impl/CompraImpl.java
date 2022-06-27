package com.unittest.app.dominio.servicio.impl;


import com.unittest.app.dominio.repositorio.ControlPagoRepo;
import com.unittest.app.dominio.modelo.*;
import com.unittest.app.dominio.modelo.dto.CalCuloPagoDto;
import com.unittest.app.dominio.servicio.Compra;
import com.unittest.app.dominio.servicio.reglas.DescuentoMembresia;
import com.unittest.app.excepcion.CompraPagoNoCompletoException;
import com.unittest.app.integracion.Membresia;
import com.unittest.app.integracion.PagoEnLinea;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public Pago ventaDeProductoPagoEnLinea(TransaccionCompra transaccionCompra, Cliente cliente) {
        Pago pago = new Pago();
        pago.setTransaccionCompra(transaccionCompra);
        TipoMembresia tipoMembresia;
        Double subtotal = transaccionCompra.obtenerSubtotal();
        if (cliente != null && membresiaSrv.membreciaVigente(cliente)) {
            tipoMembresia = membresiaSrv.obtenerMembrecia(cliente);
            Double descuento = 0.0;
            /*Strategy classes*/
            descuento = descuentoMembresias.stream()
                    .filter(desc -> desc.obtenerTipo()==tipoMembresia)
                    .findAny().get().obtenerDescuento(subtotal);

            pago.setDescuento(descuento);
        }

        pago.setSubtotal(subtotal);
        pago.setTotal(pago.getSubtotal() - pago.getDescuento());
        pago.setPorPagar(pago.getTotal());
        final CalCuloPagoDto calCuloPagoDto =  CalculoPagarLineaHelper.realizarCalculoDePago(transaccionCompra.getDatosPago(),pago);

        if(calCuloPagoDto.getPago().getPorPagar() > 0){
            throw new CompraPagoNoCompletoException("Error Pago no completo");
        }

        CalculoPagarLineaHelper.pagosAutorizados(calCuloPagoDto,pagoEnLinea);
        calCuloPagoDto.getPago().setEstatusDePago(EstatusDePago.PAGADO);
        return controlPagoRepo.guardarPago(calCuloPagoDto.getPago());
    }


}
