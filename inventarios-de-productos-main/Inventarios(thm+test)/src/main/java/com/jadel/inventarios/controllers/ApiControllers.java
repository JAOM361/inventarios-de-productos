package com.jadel.inventarios.controllers;

import com.jadel.inventarios.models.Producto;
import com.jadel.inventarios.models.Usuario;
import com.jadel.inventarios.models.Movimiento;
import com.jadel.inventarios.services.ProductoService;
import com.jadel.inventarios.services.UsuarioService;
import com.jadel.inventarios.services.MovimientoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiControllers {
    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final MovimientoService movimientoService;

    public ApiControllers(ProductoService productoService, UsuarioService usuarioService, MovimientoService movimientoService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.movimientoService = movimientoService;
    }

    // Producto endpoints
    @GetMapping("/productos")
    public List<Producto> listarProductos() { return productoService.getAllProductos(); }

    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> getProducto(@PathVariable Long id) {
        return productoService.getProductoById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/productos")
    public Producto saveProducto(@RequestBody Producto p) { return productoService.saveProducto(p); }

    // Usuario endpoints
    @GetMapping("/usuarios")
    public List<Usuario> listarUsuarios() { return usuarioService.getAllUsuarios(); }

    @PostMapping("/usuarios")
    public Usuario saveUsuario(@RequestBody Usuario u) { return usuarioService.saveUsuario(u); }

    // Movimiento endpoints
    @GetMapping("/movimientos")
    public List<Movimiento> listarMovimientos() { return movimientoService.getAllMovimientos(); }

    @PostMapping("/movimientos")
    public Movimiento saveMovimiento(@RequestBody Movimiento m) { return movimientoService.saveMovimiento(m); }
}
