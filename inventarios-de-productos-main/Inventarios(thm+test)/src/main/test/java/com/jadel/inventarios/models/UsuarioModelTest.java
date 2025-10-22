package com.jadel.inventarios.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioModelTest {

    @Test
    void constructorAndGetters() {
        Usuario u = new Usuario("Admin","a@a.com","pass","Admin");
        assertEquals("Admin", u.getNombre());
        assertEquals("a@a.com", u.getCorreo());
    }
}
