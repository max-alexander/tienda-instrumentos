package com.example.cotizaciones.repository;

import com.example.cotizaciones.model.DetalleCotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleCotizacionRepository extends JpaRepository<DetalleCotizacion, Integer> {
}
