package com.unittest.app.servicio.impl;

import com.unittest.app.Repositorio.ControlPagoRepo;
import com.unittest.app.integracion.Membresia;
import com.unittest.app.integracion.PagoEnLinea;
import com.unittest.app.servicio.Compra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class CompraImplTest {
    @Mock
    ControlPagoRepo controlPagoRepo;
    @Mock
    PagoEnLinea pagoEnLinea;
    @Mock
    Membresia membresiaSrv;

    @InjectMocks
    CompraImpl compra;

    @BeforeEach
    void setUp() {
    }

    @Test
    void ventaDeProducto() {
    }

    @Test
    void cancelacionVenta() {
    }

    @Test
    void agregarProducto() {
    }

    @Test
    void agregarDatosPago() {
    }

    @Test
    void productoCancelado() {
    }

    @Test
    void agregarCliente() {
    }
}