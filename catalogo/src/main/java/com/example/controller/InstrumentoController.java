package com.example.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.model.Instrumento;
import com.example.services.InstrumentoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/instrumentos")
public class InstrumentoController {

    @Autowired
    private InstrumentoService service;

    @GetMapping
    public List<Instrumento> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Optional<Instrumento> buscarPorId(@PathVariable int id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/marca/{marca}")
    public List<Instrumento> buscarPorMarca(@PathVariable String marca) {
        return service.buscarPorMarca(marca);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Instrumento> buscarPorTipo(@PathVariable String tipo) {
        return service.buscarPorTipo(tipo);
    }

    @PostMapping
    public Instrumento guardar(@RequestBody Instrumento instrumento) {
        return service.guardar(instrumento);
    }

    @PutMapping("/{id}")
    public Instrumento actualizar(@PathVariable int id, @RequestBody Instrumento instrumento) {
        return service.actualizar(id, instrumento);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable int id) {
        service.eliminar(id);
    }
    @GetMapping("/validar/{id}")
    public boolean validarInstrumento(@PathVariable int id) {
        return service.validarInstrumento(id);
    }
}