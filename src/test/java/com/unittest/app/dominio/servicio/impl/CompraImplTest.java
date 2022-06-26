package com.unittest.app.dominio.servicio.impl;

import com.unittest.app.dominio.modelo.*;
import com.unittest.app.dominio.repositorio.ControlPagoRepo;
import com.unittest.app.integracion.Membresia;
import com.unittest.app.integracion.PagoEnLinea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CompraImplTest {
    @Mock
    ControlPagoRepo controlPagoRepo;

    @Captor
    ArgumentCaptor<Pago> pagoCaptor;

    @Mock
    PagoEnLinea pagoEnLinea;
    @Mock
    Membresia membresiaSrv;

    @InjectMocks
    CompraImpl compra;
    TransaccionCompra transaccionCompra;
    Cliente cliente;


    @BeforeEach
    void setUp() {

        transaccionCompra = new TransaccionCompra();
        List<Producto> productos = Arrays.asList(Producto.builder().descripcionProducto("Libro A").id(1).precio(283.98).build(), Producto.builder().descripcionProducto("Libro B").id(2).precio(359.20).build());

        cliente = Cliente.builder().nickName("Nick").numeroCliente(12293232).nombre("Pedro Infante").build();


        transaccionCompra.setProductoList(productos);
        transaccionCompra.setCliente(cliente);
    }

    @Test()
    void ventaDeProductoPagoEnLineaMembresiaDiamante() {

        List<DatosPago> datosPagos = Arrays.asList(DatosPago.builder().tipoPago(TipoPago.TD).montoPagado(302.00).build(), DatosPago.builder().tipoPago(TipoPago.TD).montoPagado(320.00).build(), DatosPago.builder().tipoPago(TipoPago.TD).montoPagado(143.18).build());
        transaccionCompra.setDatosPago(datosPagos);

        Pago resultado = new Pago();
        resultado.setId(1001L);
        resultado.setEstatusDePago(EstatusDePago.PAGADO);

        Mockito.when(membresiaSrv.membreciaVigente(Mockito.any(Cliente.class))).thenReturn(Boolean.TRUE);
        Mockito.when(membresiaSrv.obtenerMembrecia(Mockito.any(Cliente.class))).thenReturn(TipoMembresia.DIAMANTE);
        Mockito.when(controlPagoRepo.guardarPago(pagoCaptor.capture())).thenReturn(resultado);
        Pago pagoRegreso = compra.ventaDeProductoPagoEnLinea(transaccionCompra, cliente);
        Pago pagoAPersistencia = pagoCaptor.getValue();
        assertEquals(pagoRegreso.getEstatusDePago(), pagoAPersistencia.getEstatusDePago(), "El pago tiene estaus diferente a PAGADO");

    }
}