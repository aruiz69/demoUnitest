package com.unittest.app.dominio.servicio.impl;

import com.unittest.app.dominio.modelo.DatosPago;
import com.unittest.app.dominio.modelo.Pago;
import com.unittest.app.dominio.modelo.TipoPago;
import com.unittest.app.dominio.modelo.dto.CalCuloPagoDto;
import com.unittest.app.excepcion.PagoNoAutorizado;
import com.unittest.app.integracion.PagoEnLinea;

import java.util.List;
import java.util.stream.Collectors;

class CalculoPagarLineaHelper {
    static CalCuloPagoDto realizarCalculoDePago(List<DatosPago> pagosDatos, Pago pago) {
        if (pagosDatos == null || pago == null) {
            throw new IllegalArgumentException("La refencia de pagosDatos o el pago no puede venir en null");
        }
        final CalCuloPagoDto calCuloPagoDto = new CalCuloPagoDto();
        calCuloPagoDto.setPago(pago);
        DatosPago accumulador = DatosPago.builder().montoPagado(0.0).build();
        pagosDatos
                .stream()
                .filter(datoPago -> datoPago.getTipoPago()== TipoPago.TD || datoPago.getTipoPago()== TipoPago.TC)
                .forEach(datosPago -> {
                    if (pago.getPorPagar() > 0) {
                        Double subCalculo = accumulador.getMontoPagado() + datosPago.getMontoPagado();
                        if (subCalculo < pago.getPorPagar()) {
                            accumulador.setMontoPagado(subCalculo);
                            calCuloPagoDto.getPagosPorAutorizar().add(datosPago);
                            pago.setPorPagar(pago.getPorPagar() - subCalculo);
                        }else if (datosPago.getMontoPagado() > pago.getPorPagar()) {
                            Double montoReducido = datosPago.getMontoPagado() - pago.getPorPagar();
                            datosPago.setMontoPagado(datosPago.getMontoPagado() - montoReducido);
                            calCuloPagoDto.getPagosPorAutorizar().add(datosPago);
                            pago.setPorPagar(pago.getPorPagar() - datosPago.getMontoPagado());
                        }
                    }
                });

        return calCuloPagoDto;
    }

    void pagosAutorizados(CalCuloPagoDto calCuloPagoDto, PagoEnLinea pagoEnLinea) {
        if(calCuloPagoDto == null || pagoEnLinea == null){
            throw new IllegalArgumentException("La refencia de calCuloPagoDto o el pagoEnLinea no puede venir en null");
        }
        calCuloPagoDto.getPagosPorAutorizar()
                .stream()
                .map(pagoEnLinea::obtenerAprobacionPago)
                .collect(Collectors.toList())
                .stream().filter(result -> result.booleanValue() == Boolean.FALSE)
                .findAny()
                .ifPresent(controlPago -> {
                    throw new PagoNoAutorizado("Pago No Aprobado " + controlPago);
                });
    }

}
