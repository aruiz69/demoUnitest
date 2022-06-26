package com.unittest.app.dominio.modelo.dto;

import com.unittest.app.dominio.modelo.TransaccionCompra;
import com.unittest.app.dominio.modelo.DatosPago;
import lombok.Data;

@Data
public class DatoPagoCuentaDto {
    DatosPago datosPago;
    TransaccionCompra transaccionCompra;
}
