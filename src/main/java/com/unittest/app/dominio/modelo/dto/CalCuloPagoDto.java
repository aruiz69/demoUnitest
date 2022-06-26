package com.unittest.app.dominio.modelo.dto;

import com.unittest.app.dominio.modelo.DatosPago;
import com.unittest.app.dominio.modelo.Pago;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CalCuloPagoDto {
    List<DatosPago> pagosPorAutorizar = new ArrayList<>();
    Pago pago;
}
