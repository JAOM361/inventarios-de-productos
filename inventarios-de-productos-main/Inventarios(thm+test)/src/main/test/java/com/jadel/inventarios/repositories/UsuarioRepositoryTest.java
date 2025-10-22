package com.jadel.inventarios.repositories;

import com.jadel.inventarios.models.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    void saveAndFindByCorreo() {
        Usuario u = new Usuario("Admin","a@a.com","pass","Admin");
        usuarioRepository.save(u);
        Optional<Usuario> res = usuarioRepository.findByCorreo("a@a.com");
        assertTrue(res.isPresent());
        assertEquals("Admin", res.get().getNombre());
    }
}
