package com.jadel.inventarios.models;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class MovimientoModelTest {

    @Test
    void constructorAndGetters() {
        Producto p = new Producto("P","D",5,10.0);
        Movimiento m = new Movimiento(Movimiento.TipoMovimiento.ENTRADA, 2, LocalDateTime.now(), p);
        assertEquals(Movimiento.TipoMovimiento.ENTRADA, m.getTipo());
        assertEquals(2, m.getCantidad());
    }
}
