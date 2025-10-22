package com.jadel.inventarios.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipo;

    @Min(1)
    private int cantidad;

    private LocalDateTime fecha;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Movimiento() {}

    public Movimiento(TipoMovimiento tipo, int cantidad, LocalDateTime fecha, Producto producto) {
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.producto = producto;
    }

    public enum TipoMovimiento { ENTRADA, SALIDA }

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}
    public TipoMovimiento getTipo(){return tipo;}
    public void setTipo(TipoMovimiento tipo){this.tipo=tipo;}
    public int getCantidad(){return cantidad;}
    public void setCantidad(int cantidad){this.cantidad=cantidad;}
    public LocalDateTime getFecha(){return fecha;}
    public void setFecha(LocalDateTime fecha){this.fecha=fecha;}
    public Producto getProducto(){return producto;}
    public void setProducto(Producto producto){this.producto=producto;}
}
