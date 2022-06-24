package com.unittest.app.modelo.dto;

import com.unittest.app.modelo.TransaccionCompra;
import com.unittest.app.modelo.Producto;
import lombok.Data;

@Data
public class ProductoCuentaDto {
    Producto producto;
    TransaccionCompra transaccionCompra;
}
