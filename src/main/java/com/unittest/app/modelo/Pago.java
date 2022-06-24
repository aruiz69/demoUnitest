package com.unittest.app.modelo;


import lombok.Data;


@Data
public class Pago {
    TransaccionCompra transaccionCompra;
    Cliente cliente;
    DatosPago datosPago;
    EstatusDePago estatusDePago;
    Double subtotal;
    Double descuento;
    Double total;

}
