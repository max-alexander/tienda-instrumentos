package com.example.cotizaciones.controller;

import com.example.cotizaciones.model.Cotizacion;
import com.example.cotizaciones.model.DetalleCotizacion;
import com.example.cotizaciones.services.CotizacionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/cotizaciones")
public class CotizacionController {

    private final CotizacionService service;

    public CotizacionController(CotizacionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Cotizacion c) {
        Cotizacion creado = service.crearCotizacion(c);
           java.net.URI uri = org.springframework.web.servlet.support.ServletUriComponentsBuilder
           .fromCurrentRequest().path("/{id}")
           .buildAndExpand(creado.getIdCotizacion()).toUri();
           return ResponseEntity.created(uri).body(creado);
    }

    @GetMapping("/{id}")
public ResponseEntity<?> obtener(@PathVariable int id) {
    java.util.Optional<Cotizacion> cotizacionOpt = service.obtener(id);
    
    if (cotizacionOpt.isPresent()) {
        return ResponseEntity.ok(cotizacionOpt.get());
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cotizacion no encontrada");
    }
}

    @GetMapping
    public List<Cotizacion> listar() {
        return service.listar();
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable int id, @RequestBody String nuevoEstado) {
        try {
            Cotizacion actualizado = service.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PostMapping("/{id}/detalles")
    public ResponseEntity<?> agregarDetalle(@PathVariable int id, @RequestBody DetalleCotizacion d) {
        try {
            Cotizacion actualizado = service.agregarDetalle(id, d);
            return ResponseEntity.ok(actualizado);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
