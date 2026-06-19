package com.example.inventario.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.inventario.model.Inventario;
import com.example.inventario.services.InventarioService;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService service;

    @GetMapping
    public List<Inventario> listarInventario() {
        return service.listarInventario();
    }

    @GetMapping("/{id}")
    public Optional<Inventario> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/instrumento/{idInstrumento}")
    public Optional<Inventario> buscarPorInstrumento(@PathVariable int idInstrumento) {
        return service.buscarPorInstrumento(idInstrumento);
    }

    @PostMapping
    public Inventario guardar(@RequestBody Inventario inventario) {
    return service.guardarInventario(inventario);
    }

    @PutMapping("/{id}/{stock}")
    public Inventario actualizarStock(@PathVariable int id,@PathVariable int stock) {
        return service.actualizarStock(id, stock);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        service.eliminar(id);
    }
}