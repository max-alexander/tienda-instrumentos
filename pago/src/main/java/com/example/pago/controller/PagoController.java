package com.example.pago.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.pago.model.Pago;
import com.example.pago.services.PagoServices;

@RestController
@RequestMapping("/pagos")

public class PagoController {

    @Autowired
    private PagoServices service;

    @GetMapping
    public List<Pago> listarPagos() {
        return service.listarPagos();
    }
    @GetMapping("/{id}")
    public Optional<Pago> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id);
    }
    
@PostMapping
public ResponseEntity<?> procesarPago(@RequestBody Pago pago) {
    try {
        Pago nuevoPago = service.procesarPago(pago);
        return ResponseEntity.ok(nuevoPago);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        service.eliminar(id);
    }
}
