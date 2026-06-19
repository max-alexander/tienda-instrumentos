package com.example.cotizaciones.repository;

import com.example.cotizaciones.model.Cotizacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CotizacionRepository extends JpaRepository<Cotizacion, Integer> {
}
