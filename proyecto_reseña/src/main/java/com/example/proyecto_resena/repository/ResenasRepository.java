package com.example.proyecto_resena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.proyecto_resena.model.Resenas;

@Repository
public interface ResenasRepository extends JpaRepository<Resenas, Integer> {
}
