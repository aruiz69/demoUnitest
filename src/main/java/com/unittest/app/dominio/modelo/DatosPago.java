package com.unittest.app.dominio.modelo;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DatosPago {
    TipoPago tipoPago;
    Cliente cliente;
    String ccv;
    String numerTarjeta;
    Double montoPagado;
    // Otros datos

}
