package com.example.proyecto_resena.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.proyecto_resena.model.Resenas;
import com.example.proyecto_resena.service.ResenasService;

@RestController
@RequestMapping("/resenas")
public class ResenasController {
    
    @Autowired
    private ResenasService service;

    @GetMapping("/listar")
    public List<Resenas> listar(){
        return service.listarTodos();
    }

    @GetMapping("/buscar/{id}")
    public Optional<Resenas> buscarPorId(@PathVariable Integer id){
        return service.buscarPorId(id);
    }

    @PostMapping("/crear")
    public Resenas crear(@RequestBody Resenas r){
        return service.gurardar(r);
    }
}
