package com.jadel.inventarios.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoModelTest {

    @Test
    void constructorAndGetters() {
        Producto p = new Producto("Ibuprofeno","Desc",10,12.5);
        assertEquals("Ibuprofeno", p.getNombre());
        assertEquals(10, p.getStock());
        assertEquals(12.5, p.getPrecio());
    }

    @Test
    void setters() {
        Producto p = new Producto();
        p.setNombre("X");
        p.setPrecio(3.3);
        assertEquals("X", p.getNombre());
        assertEquals(3.3, p.getPrecio());
    }
}
