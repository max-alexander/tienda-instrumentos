package com.example.postventa.controller;

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

import com.example.postventa.model.PostventaModel;
import com.example.postventa.services.PostventaService;

@RestController
@RequestMapping("/postventa")
public class PostventaController {

    @Autowired
    private PostventaService service;

    @GetMapping
    public List<PostventaModel> listarPostventa() { return service.listarPostventa(); }

    @GetMapping("/{id}")
    public Optional<PostventaModel> buscarPorId(@PathVariable int id) { return service.buscarPorId(id); }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody PostventaModel p) {
        try {
            PostventaModel creado = service.crearPostventa(p);
            return ResponseEntity.ok(creado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(@PathVariable int id, @RequestBody String nuevoEstado) {
        try {
            PostventaModel actualizado = service.actualizarEstado(id, nuevoEstado);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) { service.eliminar(id); }
}
