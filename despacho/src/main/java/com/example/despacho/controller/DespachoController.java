package com.example.despacho.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.despacho.model.DespachoModel;
import com.example.despacho.services.DespachoService;

@RestController
@RequestMapping("/despachos")
public class DespachoController {

    @Autowired
    private DespachoService service;

    @GetMapping
    public List<DespachoModel> listarDespachos() { return service.listarDespachos(); }

    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<DespachoModel> buscarPorPedido(@PathVariable int idPedido) {
        return service.buscarPorPedido(idPedido)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public Optional<DespachoModel> buscarPorId(@PathVariable int id) { return service.buscarPorId(id); }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody DespachoModel d) {
        try {
            DespachoModel creado = service.crearDespacho(d);
            return ResponseEntity.ok(creado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable int id, @RequestBody String nuevoEstado) {
        try {
            DespachoModel actualizado = service.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) { service.eliminar(id); }
}
