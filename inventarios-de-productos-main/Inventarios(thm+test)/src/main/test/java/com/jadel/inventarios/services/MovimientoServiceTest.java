package com.jadel.inventarios.services;

import com.jadel.inventarios.models.Producto;
import com.jadel.inventarios.models.Movimiento;
import com.jadel.inventarios.repositories.MovimientoRepository;
import com.jadel.inventarios.repositories.ProductoRepository;
import com.jadel.inventarios.services.MovimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MovimientoServiceTest {

    private MovimientoRepository movimientoRepository;
    private ProductoRepository productoRepository;
    private MovimientoService movimientoService;

    @BeforeEach
    void setup() {
        movimientoRepository = Mockito.mock(MovimientoRepository.class);
        productoRepository = Mockito.mock(ProductoRepository.class);
        movimientoService = new MovimientoService(movimientoRepository, productoRepository);
    }

    @Test
    void testSaveMovimientoEntrada() {
        Producto p = new Producto("P","D",5,10.0);
        p.setId(1L);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(p));
        Movimiento m = new Movimiento(Movimiento.TipoMovimiento.ENTRADA,3, LocalDateTime.now(), p);
        when(productoRepository.save(any(Producto.class))).thenAnswer(i -> i.getArgument(0));
        when(movimientoRepository.save(any(Movimiento.class))).thenAnswer(i -> i.getArgument(0));

        Movimiento saved = movimientoService.saveMovimiento(m);
        assertEquals(8, saved.getProducto().getStock());
    }
}
