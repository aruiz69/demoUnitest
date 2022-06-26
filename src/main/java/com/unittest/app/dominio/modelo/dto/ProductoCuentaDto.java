package com.unittest.app.dominio.modelo.dto;

import com.unittest.app.dominio.modelo.TransaccionCompra;
import com.unittest.app.dominio.modelo.Producto;
import lombok.Data;

@Data
public class ProductoCuentaDto {
    Producto producto;
    TransaccionCompra transaccionCompra;
}
