package com.example.proyecto_resena.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.proyecto_resena.model.Resenas;
import com.example.proyecto_resena.repository.ResenasRepository;

@Service
public class ResenasService {

    @Autowired
    private ResenasRepository repo;

    public List<Resenas> listarTodos(){
        return repo.findAll();
    }

    public Optional<Resenas> buscarPorId(int id){
        return repo.findById(id);
    }

    public Resenas gurardar(Resenas r){
        r.setFecha(LocalDate.now());
        return repo.save(r);
    }
}
