package com.unittest.app.modelo.dto;

import com.unittest.app.modelo.TransaccionCompra;
import com.unittest.app.modelo.DatosPago;
import lombok.Data;

@Data
public class DatoPagoCuentaDto {
    DatosPago datosPago;
    TransaccionCompra transaccionCompra;
}
