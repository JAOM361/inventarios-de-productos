package com.jadel.inventarios.services;

import com.jadel.inventarios.models.Producto;
import com.jadel.inventarios.repositories.ProductoRepository;
import com.jadel.inventarios.services.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoServiceTest {

    private ProductoRepository productoRepository;
    private ProductoService productoService;

    @BeforeEach
    void setup() {
        productoRepository = Mockito.mock(ProductoRepository.class);
        productoService = new ProductoService(productoRepository);
    }

    @Test
    void testListarProductos() {
        Producto p1 = new Producto("Ibuprofeno","Desc",10,12.5);
        Producto p2 = new Producto("Paracetamol","Desc",5,8.0);

        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Producto> productos = productoService.getAllProductos();

        assertEquals(2, productos.size());
        verify(productoRepository, times(1)).findAll();
    }
}
