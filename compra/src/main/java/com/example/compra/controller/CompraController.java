package com.example.compra.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.compra.model.Compra;
import com.example.compra.services.CompraServices;

@RestController
@RequestMapping("/compras")

public class CompraController {

    @Autowired
    private CompraServices service;

    @GetMapping
    public List<Compra> listarCompras() {
        return service.listarCompras();
    }

    @GetMapping("/{id}")
    public Optional<Compra> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    public Compra realizarCompra(@RequestBody Compra compra) {
        return service.realizarCompra(compra);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        service.eliminar(id);
    }
}