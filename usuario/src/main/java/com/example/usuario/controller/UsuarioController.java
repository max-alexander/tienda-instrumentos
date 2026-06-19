package com.example.usuario.controller;

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

import com.example.usuario.model.Direccion;
import com.example.usuario.model.LoginRequest;
import com.example.usuario.model.UsuarioModel;
import com.example.usuario.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<UsuarioModel> listarUsuarios() { return service.listarUsuarios(); }

    @GetMapping("/{id}")
    public Optional<UsuarioModel> buscarPorId(@PathVariable int id) { return service.buscarPorId(id); }

    @GetMapping("/validar/{id}")
    public ResponseEntity<Boolean> validarUsuario(@PathVariable int id) {
        return ResponseEntity.ok(service.validarUsuario(id));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = service.login(request);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody UsuarioModel u) {
        try {
            UsuarioModel creado = service.crearUsuario(u);
            return ResponseEntity.ok(creado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody UsuarioModel u) {
        try {
            UsuarioModel actualizado = service.actualizarUsuario(id, u);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/rol")
    public ResponseEntity<?> actualizarRol(@PathVariable int id, @RequestBody Integer idRol) {
        try {
            UsuarioModel actualizado = service.actualizarRol(id, idRol);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/{id}/direcciones")
    public ResponseEntity<?> agregarDireccion(@PathVariable int id, @RequestBody Direccion d) {
        try {
            Direccion creada = service.agregarDireccion(id, d);
            return ResponseEntity.ok(creada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/direcciones/{id}")
    public ResponseEntity<?> actualizarDireccion(@PathVariable int id, @RequestBody Direccion d) {
        try {
            Direccion actual = service.actualizarDireccion(id, d);
            return ResponseEntity.ok(actual);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) { service.eliminarUsuario(id); }
}
