package com.unittest.app.modelo;

import lombok.Data;

@Data
public class Cliente {
    long numeroCliente;
    Membresia membresia;
    String nombre;
    String nickName;
    //Otros
}
