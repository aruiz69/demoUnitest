package com.unittest.app.dominio.modelo;


import lombok.Data;


@Data
public class Pago {
    Long id;
    Integer referencia;
    TransaccionCompra transaccionCompra;
    Cliente cliente;
    DatosPago datosPago;
    EstatusDePago estatusDePago;
    Double subtotal;
    Double descuento;
    Double porPagar;
    Double total;

}
