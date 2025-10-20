package com.jadel.inventarios.controllers;

import com.jadel.inventarios.models.Producto;
import com.jadel.inventarios.models.Usuario;
import com.jadel.inventarios.models.Movimiento;
import com.jadel.inventarios.services.ProductoService;
import com.jadel.inventarios.services.UsuarioService;
import com.jadel.inventarios.services.MovimientoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ViewController {

    private final ProductoService productoService;
    private final UsuarioService usuarioService;
    private final MovimientoService movimientoService;

    public ViewController(ProductoService productoService, UsuarioService usuarioService, MovimientoService movimientoService) {
        this.productoService = productoService;
        this.usuarioService = usuarioService;
        this.movimientoService = movimientoService;
    }

    // Páginas principales
    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    // ---------------------- PRODUCTOS ----------------------
    @GetMapping("/productos")
    public String productos(Model model) {
        model.addAttribute("productos", productoService.getAllProductos());
        return "productos";
    }

    @PostMapping("/productos")
    public String agregarProducto(@ModelAttribute Producto producto) {
        productoService.saveProducto(producto);
        return "redirect:/productos";
    }

    // ---------------------- USUARIOS ----------------------
    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "usuarios";
    }

    @PostMapping("/usuarios")
    public String agregarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.saveUsuario(usuario);
        return "redirect:/usuarios";
    }

    // ---------------------- MOVIMIENTOS ----------------------
    @GetMapping("/movimientos")
    public String movimientos(Model model) {
        model.addAttribute("movimientos", movimientoService.getAllMovimientos());
        model.addAttribute("productos", productoService.getAllProductos());
        return "movimientos";
    }
    @PostMapping("/movimientos")
    public String registrarMovimiento(
        @RequestParam("tipo") String tipo,
        @RequestParam("productoId") Long productoId,
        @RequestParam("cantidad") int cantidad) {

    Producto producto = productoService.getProductoById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    Movimiento movimiento = new Movimiento();
    movimiento.setTipo(Movimiento.TipoMovimiento.valueOf(tipo));
    movimiento.setProducto(producto);
    movimiento.setCantidad(cantidad);
    movimiento.setFecha(java.time.LocalDateTime.now());

    movimientoService.saveMovimiento(movimiento);
    return "redirect:/movimientos";
}



}
