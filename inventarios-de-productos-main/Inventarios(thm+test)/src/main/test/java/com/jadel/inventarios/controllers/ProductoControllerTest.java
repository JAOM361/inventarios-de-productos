package com.jadel.inventarios.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jadel.inventarios.models.Producto;
import com.jadel.inventarios.services.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testListar() throws Exception {
        when(productoService.getAllProductos()).thenReturn(Arrays.asList(new Producto("A","D",1,1.0)));
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk());
    }

    @Test
    void testGuardar_Invalid() throws Exception {
        Producto p = new Producto("", "desc", -1, -5.0);
        String json = objectMapper.writeValueAsString(p);
        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isBadRequest());
    }
}
