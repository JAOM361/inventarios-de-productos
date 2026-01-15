package com.jadel.inventarios.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jadel.inventarios.models.Movimiento;
import com.jadel.inventarios.models.Producto;
import com.jadel.inventarios.services.MovimientoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MovimientoController.class)
public class MovimientoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovimientoService movimientoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGuardarInvalid() throws Exception {
        Movimiento m = new Movimiento(null, 0, LocalDateTime.now(), null);
        String json = objectMapper.writeValueAsString(m);
        mockMvc.perform(post("/movimientos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
