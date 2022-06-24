package com.unittest.app.modelo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Producto {
    int id;
    String descripcionProducto;
    double precio;
    //Otros datos
}
