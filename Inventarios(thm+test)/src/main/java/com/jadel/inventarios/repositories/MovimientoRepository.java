package com.jadel.inventarios.repositories;

import com.jadel.inventarios.models.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovimientoRepository extends JpaRepository<Movimiento, Long> {
}
