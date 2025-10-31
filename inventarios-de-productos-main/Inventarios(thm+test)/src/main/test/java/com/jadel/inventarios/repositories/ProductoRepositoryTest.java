package com.jadel.inventarios.repositories;

import com.jadel.inventarios.models.Producto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ProductoRepositoryTest {

    @Autowired
    private ProductoRepository productoRepository;

    @Test
    void saveAndFind() {
        Producto p = new Producto("Ibuprofeno","Desc",10,5.0);
        productoRepository.save(p);
        List<Producto> found = productoRepository.findByNombreContainingIgnoreCase("ibu");
        assertFalse(found.isEmpty());
        assertEquals("Ibuprofeno", found.get(0).getNombre());
    }
}
