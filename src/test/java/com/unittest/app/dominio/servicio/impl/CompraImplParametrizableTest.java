package com.unittest.app.dominio.servicio.impl;

import com.unittest.app.dominio.modelo.*;
import com.unittest.app.dominio.repositorio.ControlPagoRepo;
import com.unittest.app.dominio.servicio.reglas.DescuentoMembresia;
import com.unittest.app.dominio.servicio.reglas.Diamante;
import com.unittest.app.dominio.servicio.reglas.Oro;
import com.unittest.app.dominio.servicio.reglas.Plata;
import com.unittest.app.integracion.Membresia;
import com.unittest.app.integracion.PagoEnLinea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CompraImplParametrizableTest {
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

    @ParameterizedTest
    @MethodSource("getParameters")
    void ventaDeProductoPagoEnLineaParametrizado(TipoMembresia membresia, Pago pago, DescuentoMembresia descuentoMembresia) {

        List<DatosPago> datosPagos = Arrays.asList(
                DatosPago.builder()
                        .tipoPago(TipoPago.TD)
                        .montoPagado(643.18)
                        .build()
        );
        transaccionCompra.setDatosPago(datosPagos);

        Mockito.when(membresiaSrv.membreciaVigente(Mockito.any(Cliente.class))).thenReturn(Boolean.TRUE);
        Mockito.when(membresiaSrv.obtenerMembrecia(Mockito.any(Cliente.class))).thenReturn(membresia);
        Mockito.when(controlPagoRepo.guardarPago(pagoCaptor.capture())).thenReturn(pago);
        Mockito.when(pagoEnLinea.obtenerAprobacionPago(Mockito.any(DatosPago.class))).thenReturn(Boolean.TRUE);
        Mockito.when(descuentoMembresias.stream()).thenReturn(Arrays.stream(new DescuentoMembresia[]{descuentoMembresia}));
        Pago pagoRegreso = compra.ventaDeProductoPagoEnLinea(transaccionCompra, cliente);
        Pago pagoAPersistencia = pagoCaptor.getValue();
        assertEquals(pagoRegreso.getEstatusDePago() , pagoAPersistencia.getEstatusDePago(), "El pago tiene estaus diferente a PAGADO");
        assertEquals(Math.floor((pagoRegreso.getTotal() * 100) / 100) , Math.floor((pagoAPersistencia.getTotal() * 100) / 100), "Pago total erroneo");

    }

    private static Stream<Arguments> getParameters() {
        Pago pagoDiamente = new Pago();
        pagoDiamente.setId(1001L);
        pagoDiamente.setEstatusDePago(EstatusDePago.PAGADO);
        pagoDiamente.setTotal(643.18 * .85);

        Pago pagoOro = new Pago();
        pagoOro.setId(1001L);
        pagoOro.setEstatusDePago(EstatusDePago.PAGADO);
        pagoOro.setTotal(643.18 * .9);

        Pago pagoPlata = new Pago();
        pagoPlata.setId(1001L);
        pagoPlata.setEstatusDePago(EstatusDePago.PAGADO);
        pagoPlata.setTotal(643.18 * .95);

        return Stream.of(
                Arguments.of(TipoMembresia.DIAMANTE, pagoDiamente, new Diamante()),
                Arguments.of(TipoMembresia.ORO, pagoOro, new Oro()),
                Arguments.of(TipoMembresia.PLATA, pagoPlata, new Plata())
        );
    }
}