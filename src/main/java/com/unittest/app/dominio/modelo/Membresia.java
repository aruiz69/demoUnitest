package com.unittest.app.dominio.modelo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Membresia {
    TipoMembresia tipoMembresia;
    LocalDateTime vigencia;
    //Otros...
}
