package com.jadel.inventarios.services;

import com.jadel.inventarios.models.Usuario;
import com.jadel.inventarios.repositories.UsuarioRepository;
import com.jadel.inventarios.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioServiceTest {

    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        usuarioRepository = Mockito.mock(UsuarioRepository.class);
        usuarioService = new UsuarioService(usuarioRepository);
    }

    @Test
    void testListarUsuarios() {
        Usuario u1 = new Usuario("Admin","a@a.com","123","Admin");
        Usuario u2 = new Usuario("User","u@u.com","123","User");
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(u1, u2));

        List<Usuario> usuarios = usuarioService.getAllUsuarios();
        assertEquals(2, usuarios.size());
        verify(usuarioRepository, times(1)).findAll();
    }
}
