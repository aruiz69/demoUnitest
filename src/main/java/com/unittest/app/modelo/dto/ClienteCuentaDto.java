package com.unittest.app.modelo.dto;

import com.unittest.app.modelo.Cliente;
import com.unittest.app.modelo.TransaccionCompra;
import lombok.Data;

@Data
public class ClienteCuentaDto {
    Cliente cliente;
    TransaccionCompra transaccionCompra;
}
