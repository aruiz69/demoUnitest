package com.unittest.app.dominio.modelo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Cliente {
    long numeroCliente;
    String nombre;
    String nickName;
    //Otros
}
