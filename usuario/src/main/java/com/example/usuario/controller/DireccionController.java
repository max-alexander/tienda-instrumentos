package com.example.usuario.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.usuario.services.UsuarioService;

@RestController
@RequestMapping("/direcciones")
public class DireccionController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/validar/{idDireccion}")
    public ResponseEntity<Boolean> validarDireccion(
            @PathVariable int idDireccion,
            @RequestParam int idUsuario) {
        return ResponseEntity.ok(service.validarDireccion(idDireccion, idUsuario));
    }
}
