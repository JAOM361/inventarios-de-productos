package com.jadel.inventarios.controllers;

import com.jadel.inventarios.models.Producto;
import com.jadel.inventarios.models.Usuario;
import com.jadel.inventarios.models.Movimiento;
import com.jadel.inventarios.services.ProductoService;
import com.jadel.inventarios.services.UsuarioService;
import com.jadel.inventarios.services.MovimientoService;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.multipart.MultipartFile;
import java.nio.file.*;
import java.io.IOException;
import jakarta.servlet.http.HttpSession;

import com.jadel.inventarios.models.Producto;
import com.jadel.inventarios.services.ProductoService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;


@SuppressWarnings("unused")
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

    // Redirige "/" hacia /inicio
    @GetMapping("/")
    public String raiz() {
        return "redirect:/inicio";
    }

    // Página de inicio de sesión
    @GetMapping("/inicio")
    public String mostrarInicio() {
        return "inicio";
    }

    @PostMapping("/inicio")
    public String procesarInicioSesion(
            @RequestParam String correo,
            @RequestParam String contrasena,
            HttpSession session) {

        return usuarioService.buscarPorCorreo(correo)
                .filter(u -> u.getContrasena().equals(contrasena))
                .map(u -> {
                    session.setAttribute("usuarioActual", u);
                    return "redirect:/index";
                })
                .orElse("redirect:/inicio?error=true");
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate();
        return "redirect:/inicio";
    }
    // Página Principal

@GetMapping("/index")
public String index(HttpSession session, Model model) {
    if (session.getAttribute("usuarioActual") == null) {
        return "redirect:/"; // redirige al login si no hay sesión
    }
    model.addAttribute("usuario", session.getAttribute("usuarioActual"));
    model.addAttribute("productos", productoService.getAllProductos());
    return "index"; // ← tu vista principal
}


    // ---------------------- PRODUCTOS ----------------------
@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public String listarProductos(Model model, HttpSession session) {
        if (session.getAttribute("usuarioActual") == null) {
            return "redirect:/inicio";
        }
        model.addAttribute("usuario", session.getAttribute("usuarioActual"));
        model.addAttribute("productos", productoService.getAllProductos());
        return "productos";
    }

    @PostMapping
    public String agregarProducto(@ModelAttribute Producto producto,
                                  @RequestParam("imagenArchivo") MultipartFile imagenArchivo) {
        try {
            if (!imagenArchivo.isEmpty()) {
                String nombreArchivo = System.currentTimeMillis() + "_" + imagenArchivo.getOriginalFilename();
                Path ruta = Paths.get("uploads/" + nombreArchivo);
                if (!Files.exists(ruta.getParent())) {
                    Files.createDirectories(ruta.getParent());
                }
                Files.copy(imagenArchivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
                producto.setImagen(nombreArchivo);
            }
            productoService.saveProducto(producto);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al guardar producto con imagen", e);
        }
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioActual") == null) {
            return "redirect:/inicio";
        }
        Producto producto = productoService.getProductoById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        model.addAttribute("usuario", session.getAttribute("usuarioActual"));
        model.addAttribute("producto", producto);
        return "editar_producto";
    }

    @PostMapping("/editar/{id}")
    public String actualizarProducto(@PathVariable Long id,
                                     @ModelAttribute Producto productoActualizado,
                                     @RequestParam("imagenArchivo") MultipartFile imagenArchivo) {
        try {
            Producto producto = productoService.getProductoById(id)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            producto.setNombre(productoActualizado.getNombre());
            producto.setDescripcion(productoActualizado.getDescripcion());
            producto.setPrecio(productoActualizado.getPrecio());
            producto.setStock(productoActualizado.getStock());

            if (!imagenArchivo.isEmpty()) {
                String nombreArchivo = System.currentTimeMillis() + "_" + imagenArchivo.getOriginalFilename();
                Path ruta = Paths.get("uploads/" + nombreArchivo);
                Files.copy(imagenArchivo.getInputStream(), ruta, StandardCopyOption.REPLACE_EXISTING);
                producto.setImagen(nombreArchivo);
            }

            productoService.saveProducto(producto);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/productos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.deleteProducto(id);
        return "redirect:/productos";
    }
}
    // ---------------------- USUARIOS ----------------------
    @GetMapping("/usuarios")
    public String usuarios(Model model, HttpSession session) {
        if (session.getAttribute("usuarioActual") == null) {
            return "redirect:/inicio";
        }
        model.addAttribute("usuario", session.getAttribute("usuarioActual"));
        model.addAttribute("usuarios", usuarioService.getAllUsuarios());
        return "usuarios";
    }

    @PostMapping("/usuarios")
    public String agregarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.saveUsuario(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioActual") == null) {
            return "redirect:/inicio";
        }
        Usuario usuario = usuarioService.getUsuarioById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        model.addAttribute("usuario", session.getAttribute("usuarioActual"));
        model.addAttribute("usuarioEditado", usuario);
        return "editar_usuario";
    }

    @PostMapping("/usuarios/editar/{id}")
    public String actualizarUsuario(@PathVariable Long id, @ModelAttribute Usuario usuarioActualizado) {
        usuarioService.updateUsuario(id, usuarioActualizado);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return "redirect:/usuarios";
    }

    // ---------------------- MOVIMIENTOS ----------------------
    @GetMapping("/movimientos")
    public String movimientos(Model model, HttpSession session) {
        if (session.getAttribute("usuarioActual") == null) {
            return "redirect:/inicio";
        }
        model.addAttribute("usuario", session.getAttribute("usuarioActual"));
        model.addAttribute("movimientos", movimientoService.getAllMovimientos());
        model.addAttribute("productos", productoService.getAllProductos());
        return "movimientos";
    }

@PostMapping("/movimientos")
public String registrarMovimiento(
        @RequestParam("tipo") String tipo,
        @RequestParam("productoId") Long productoId,
        @RequestParam("cantidad") int cantidad,
        HttpSession session) { // <-- importante

    // Verificar sesión activa
    Object usuarioObj = session.getAttribute("usuarioActual");
    if (usuarioObj == null) {
        return "redirect:/inicio";
    }

    Usuario usuario = (Usuario) usuarioObj;

    // Buscar el producto
    Producto producto = productoService.getProductoById(productoId)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

    // Crear el movimiento con usuario
    Movimiento movimiento = new Movimiento();
    movimiento.setTipo(Movimiento.TipoMovimiento.valueOf(tipo));
    movimiento.setProducto(producto);
    movimiento.setCantidad(cantidad);
    movimiento.setFecha(java.time.LocalDateTime.now());
    movimiento.setUsuario(usuario); // ← Aquí se asocia el usuario actual

    movimientoService.saveMovimiento(movimiento);

    return "redirect:/movimientos";
}


    @GetMapping("/movimientos/editar/{id}")
    public String editarMovimiento(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuarioActual") == null) {
            return "redirect:/inicio";
        }
        Movimiento movimiento = movimientoService.getMovimientoById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        model.addAttribute("usuario", session.getAttribute("usuarioActual"));
        model.addAttribute("movimiento", movimiento);
        model.addAttribute("productos", productoService.getAllProductos());
        return "editar_movimiento";
    }

    @PostMapping("/movimientos/editar/{id}")
    public String actualizarMovimiento(@PathVariable Long id, @ModelAttribute Movimiento movimientoActualizado) {
        Movimiento movimiento = movimientoService.getMovimientoById(id)
                .orElseThrow(() -> new RuntimeException("Movimiento no encontrado"));
        movimiento.setTipo(movimientoActualizado.getTipo());
        movimiento.setCantidad(movimientoActualizado.getCantidad());
        movimientoService.saveMovimiento(movimiento);
        return "redirect:/movimientos";
    }

    @GetMapping("/movimientos/eliminar/{id}")
    public String eliminarMovimiento(@PathVariable Long id) {
        movimientoService.deleteMovimiento(id);
        return "redirect:/movimientos";
    }

    // ---------------------- REGISTRO ----------------------
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute Usuario usuario, HttpSession session) {
        usuarioService.saveUsuario(usuario);
        session.setAttribute("usuarioActual", usuario);
        return "redirect:/index";
    }
}
