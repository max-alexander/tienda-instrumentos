package com.example.cotizaciones.services;

import com.example.cotizaciones.model.Cotizacion;
import com.example.cotizaciones.model.DetalleCotizacion;
import com.example.cotizaciones.repository.CotizacionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CotizacionService {

    private static final String USUARIO_VALIDAR_URL = "http://localhost:8089/usuarios/validar/";
    private static final String INSTRUMENTO_VALIDAR_URL = "http://localhost:8087/instrumentos/validar/";

    private final CotizacionRepository cotizacionRepository;
    private final RestTemplate restTemplate;

    public CotizacionService(CotizacionRepository cotizacionRepository, RestTemplate restTemplate) {
        this.cotizacionRepository = cotizacionRepository;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public Cotizacion crearCotizacion(Cotizacion c) {
        if (c.getIdCliente() <= 0) {
            throw new IllegalArgumentException("clienteId es obligatorio");
        }
        validarExistencia(USUARIO_VALIDAR_URL + c.getIdCliente(), "Cliente no existe");

        List<DetalleCotizacion> detalles = c.getDetalles();
        if (detalles == null || detalles.isEmpty()) {
            throw new IllegalArgumentException("La cotización debe contener al menos un detalle");
        }

        List<DetalleCotizacion> incoming = new ArrayList<>(detalles);
        c.getDetalles().clear();
        incoming.forEach(c::addDetalle);

        int total = 0;
        for (DetalleCotizacion d : c.getDetalles()) {
            if (d.getIdInstrumento() <= 0) {
                throw new IllegalArgumentException("idInstrumento es obligatorio en cada detalle");
            }
            validarExistencia(INSTRUMENTO_VALIDAR_URL + d.getIdInstrumento(), "Instrumento no existe: " + d.getIdInstrumento());
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

    private void validarExistencia(String url, String mensaje) {
        try {
            Boolean existe = restTemplate.getForObject(url, Boolean.class);
            if (!Boolean.TRUE.equals(existe)) {
                throw new IllegalArgumentException(mensaje);
            }
        } catch (RestClientException e) {
            throw new IllegalArgumentException("Servicio externo no disponible: " + e.getMessage());
        }
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
