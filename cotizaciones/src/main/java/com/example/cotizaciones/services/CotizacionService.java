package com.example.cotizaciones.services;

import com.example.cotizaciones.model.Cotizacion;
import com.example.cotizaciones.model.DetalleCotizacion;
import com.example.cotizaciones.repository.CotizacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CotizacionService {

    private final CotizacionRepository cotizacionRepository;

    public CotizacionService(CotizacionRepository cotizacionRepository) {
        this.cotizacionRepository = cotizacionRepository;
    }

    @Transactional
    public Cotizacion crearCotizacion(Cotizacion c) {
        if (c.getIdCliente() <= 0) {
            throw new IllegalArgumentException("clienteId es obligatorio");
        }
        List<DetalleCotizacion> detalles = c.getDetalles();
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("La cotización debe contener al menos un detalle");
        }

        int total = 0;
        for (DetalleCotizacion d : detalles) {
            if (d.getCantidad() <= 0) throw new IllegalArgumentException("cantidad debe ser > 0");
            if (d.getPrecioUnitario() <= 0) throw new IllegalArgumentException("precioUnitario debe ser mayor que 0");

            int expected = d.getPrecioUnitario() * d.getCantidad();
            if (d.getSubtotal() != expected) {
                d.setSubtotal(expected);
            }
            total += d.getSubtotal();
        }
        c.setMontoTotal(total);

        return cotizacionRepository.save(c);
    }

    public Optional<Cotizacion> obtener(int id) {
        return cotizacionRepository.findById(id);
    }

    public List<Cotizacion> listar() {
        return cotizacionRepository.findAll();
    }

    @Transactional
    public Cotizacion actualizarEstado(int id, String estado) {
        Cotizacion c = cotizacionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cotizacion no encontrada"));
        c.setEstado(estado);
        return cotizacionRepository.save(c);
    }

    @Transactional
    public Cotizacion agregarDetalle(int id, DetalleCotizacion d) {
        Cotizacion c = cotizacionRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cotizacion no encontrada"));
        if (d.getCantidad() <= 0) throw new IllegalArgumentException("cantidad debe ser > 0");
        if (d.getPrecioUnitario() <= 0) throw new IllegalArgumentException("precioUnitario debe ser mayor que 0");
        int subtotal = d.getPrecioUnitario() * d.getCantidad();
        d.setSubtotal(subtotal);
        c.addDetalle(d);
        c.setMontoTotal(c.getMontoTotal() + subtotal);
        return cotizacionRepository.save(c);
    }
}
