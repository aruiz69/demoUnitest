package com.unittest.app.dominio.servicio.impl;

import com.unittest.app.dominio.modelo.*;
import com.unittest.app.dominio.repositorio.ControlPagoRepo;
import com.unittest.app.dominio.servicio.reglas.DescuentoMembresia;
import com.unittest.app.dominio.servicio.reglas.Diamante;
import com.unittest.app.excepcion.CompraPagoNoCompletoException;
import com.unittest.app.integracion.Membresia;
import com.unittest.app.integracion.PagoEnLinea;
import org.junit.jupiter.api.Assertions;
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

    @Mock
    List<DescuentoMembresia> descuentoMembresias;

    @InjectMocks
    CompraImpl compra;
    TransaccionCompra transaccionCompra;
    Cliente cliente;


    @BeforeEach
    void setUp() {

        transaccionCompra = new TransaccionCompra();
        List<Producto> productos = Arrays.asList(Producto.builder()
                        .descripcionProducto("Libro A")
                        .id(1)
                        .precio(283.98).build(),
                Producto.builder()
                        .descripcionProducto("Libro B")
                        .id(2)
                        .precio(359.20).build()
        );

        cliente = Cliente.builder()
                .nickName("Nick")
                .numeroCliente(12293232)
                .nombre("Pedro Infante")
                .build();


        transaccionCompra.setProductoList(productos);
        transaccionCompra.setCliente(cliente);
    }

    @Test()
    void ventaDeProductoPagoEnLineaPagoNoCompleto() {

        List<DatosPago> datosPagos = Arrays.asList(
                DatosPago.builder()
                        .tipoPago(TipoPago.TD)
                        .montoPagado(500.00)
                        .build()
        );
        transaccionCompra.setDatosPago(datosPagos);
        Mockito.when(descuentoMembresias.stream()).thenReturn(Arrays.stream(new DescuentoMembresia[]{new Diamante()}));
        Mockito.when(membresiaSrv.membreciaVigente(Mockito.any(Cliente.class))).thenReturn(Boolean.TRUE);
        Mockito.when(membresiaSrv.obtenerMembrecia(Mockito.any(Cliente.class))).thenReturn(TipoMembresia.DIAMANTE);
        CompraPagoNoCompletoException data = Assertions.assertThrows(CompraPagoNoCompletoException.class,
                () -> compra.ventaDeProductoPagoEnLinea(transaccionCompra, cliente));
        assertEquals("Error Pago no completo", data.getMessage(), "Mensaje error no esperado");
    }

    @Test()
    void ventaDeProductoPagoEnLineaPagoNoAprobado() {

        List<DatosPago> datosPagos = Arrays.asList(
                DatosPago.builder()
                        .tipoPago(TipoPago.TD)
                        .montoPagado(700.00)
                        .build()
        );
        transaccionCompra.setDatosPago(datosPagos);
        Mockito.when(membresiaSrv.membreciaVigente(Mockito.any(Cliente.class))).thenReturn(Boolean.TRUE);
        Mockito.when(membresiaSrv.obtenerMembrecia(Mockito.any(Cliente.class))).thenReturn(TipoMembresia.DIAMANTE);
        Mockito.when(pagoEnLinea.obtenerAprobacionPago(Mockito.any(DatosPago.class))).thenReturn(Boolean.FALSE);
        Mockito.when(descuentoMembresias.stream()).thenReturn(Arrays.stream(new DescuentoMembresia[]{new Diamante()}));
        IllegalStateException data = Assertions.assertThrows(IllegalStateException.class,
                () -> compra.ventaDeProductoPagoEnLinea(transaccionCompra, cliente));
        assertEquals("Pago No Aprobado ", data.getMessage(), "Mensaje error no esperado");
    }

    @Test()
    void ventaDeProductoPagoEnLineaMembresiaDiamante() {

        List<DatosPago> datosPagos = Arrays.asList(
                DatosPago.builder()
                        .tipoPago(TipoPago.TD)
                        .montoPagado(302.00)
                        .build(),
                DatosPago.builder()
                        .tipoPago(TipoPago.TD)
                        .montoPagado(320.00)
                        .build(),
                DatosPago.builder()
                        .tipoPago(TipoPago.TD)
                        .montoPagado(143.18)
                        .build());
        transaccionCompra.setDatosPago(datosPagos);

        Pago resultado = new Pago();
        resultado.setId(1001L);
        resultado.setEstatusDePago(EstatusDePago.PAGADO);

        Mockito.when(membresiaSrv.membreciaVigente(Mockito.any(Cliente.class))).thenReturn(Boolean.TRUE);
        Mockito.when(membresiaSrv.obtenerMembrecia(Mockito.any(Cliente.class))).thenReturn(TipoMembresia.DIAMANTE);
        Mockito.when(controlPagoRepo.guardarPago(pagoCaptor.capture())).thenReturn(resultado);
        Mockito.when(pagoEnLinea.obtenerAprobacionPago(Mockito.any(DatosPago.class))).thenReturn(Boolean.TRUE);
        Mockito.when(descuentoMembresias.stream()).thenReturn(Arrays.stream(new DescuentoMembresia[]{new Diamante()}));
        Pago pagoRegreso = compra.ventaDeProductoPagoEnLinea(transaccionCompra, cliente);
        Pago pagoAPersistencia = pagoCaptor.getValue();
        assertEquals(pagoRegreso.getEstatusDePago(), pagoAPersistencia.getEstatusDePago(), "El pago tiene estaus diferente a PAGADO");

    }
}