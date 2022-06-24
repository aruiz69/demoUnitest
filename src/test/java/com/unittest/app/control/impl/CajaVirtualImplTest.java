package com.unittest.app.control.impl;

import com.unittest.app.Repositorio.ProductoRepo;
import com.unittest.app.modelo.Producto;
import com.unittest.app.servicio.Compra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.Assert;

@ExtendWith(MockitoExtension.class)
class CajaVirtualImplTest {
    @Mock
    Compra compra;
    @Mock
    ProductoRepo productoRepo;

    @InjectMocks
    CajaVirtualImpl cajaVirtual;

    @BeforeEach
    public void befor(){
        Producto p1 = Producto.builder()
                .descripcionProducto("Producto Prueba")
                .precio(200.50)
                .id(1)
                .build();
        Mockito.when(productoRepo.obtenerProducto(Mockito.any(Integer.class))).thenReturn(p1);

    }
    @Test
    void consultarProducto() {
        Producto p1Result = cajaVirtual.consultarProducto(1);
        Assert.notNull(p1Result, "El producto es nulo");
        Assert.isTrue(p1Result.getId() == 1, "El Id no corresponde al esperado");
        Assert.isTrue(p1Result.getPrecio() == 200.50, "EL precio es incorrecto");
    }
}