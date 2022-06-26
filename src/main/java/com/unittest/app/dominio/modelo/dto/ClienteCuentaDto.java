package com.unittest.app.dominio.modelo.dto;

import com.unittest.app.dominio.modelo.Cliente;
import com.unittest.app.dominio.modelo.TransaccionCompra;
import lombok.Data;

@Data
public class ClienteCuentaDto {
    Cliente cliente;
    TransaccionCompra transaccionCompra;
}
