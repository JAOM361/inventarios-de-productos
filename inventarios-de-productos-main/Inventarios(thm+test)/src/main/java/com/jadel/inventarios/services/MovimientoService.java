package com.jadel.inventarios.services;

import com.jadel.inventarios.models.Movimiento;
import com.jadel.inventarios.models.Producto;
import com.jadel.inventarios.repositories.MovimientoRepository;
import com.jadel.inventarios.repositories.ProductoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoService {
    private final MovimientoRepository movimientoRepository;
    private final ProductoRepository productoRepository;

    public MovimientoService(MovimientoRepository movimientoRepository, ProductoRepository productoRepository) {
        this.movimientoRepository = movimientoRepository;
        this.productoRepository = productoRepository;
    }

    public List<Movimiento> getAllMovimientos() {
        return movimientoRepository.findAll();
    }

    public Optional<Movimiento> getMovimientoById(Long id) {
        return movimientoRepository.findById(id);
    }

    public Movimiento saveMovimiento(Movimiento movimiento) {
        Producto p = movimiento.getProducto();
        if (p != null && p.getId() != null) {
            Producto prod = productoRepository.findById(p.getId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            if (movimiento.getTipo() == Movimiento.TipoMovimiento.ENTRADA) {
                prod.setStock(prod.getStock() + movimiento.getCantidad());
            } else if (movimiento.getTipo() == Movimiento.TipoMovimiento.SALIDA) {
                prod.setStock(prod.getStock() - movimiento.getCantidad());
            }
            productoRepository.save(prod);
            movimiento.setFecha(LocalDateTime.now());
            movimiento.setProducto(prod);
        }
        return movimientoRepository.save(movimiento);
    }

    public void deleteMovimiento(Long id) {
        movimientoRepository.deleteById(id);
    }
}
