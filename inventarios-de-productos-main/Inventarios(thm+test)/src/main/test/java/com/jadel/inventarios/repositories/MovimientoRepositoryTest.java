package com.jadel.inventarios.repositories;

import com.jadel.inventarios.models.Movimiento;
import com.jadel.inventarios.models.Producto;
import com.jadel.inventarios.models.Movimiento.TipoMovimiento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MovimientoRepositoryTest {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void saveMovimiento() {
        Producto p = new Producto("P","D",5,10.0);
        productoRepository.save(p);
        Movimiento m = new Movimiento(TipoMovimiento.ENTRADA,2, LocalDateTime.now(), p);
        movimientoRepository.save(m);
        assertNotNull(m.getId());
    }
}
