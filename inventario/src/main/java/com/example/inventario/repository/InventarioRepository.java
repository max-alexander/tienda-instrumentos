package com.example.inventario.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.inventario.model.Inventario;
import java.util.Optional;

public interface InventarioRepository
        extends JpaRepository<Inventario, Integer> {

    Optional<Inventario> findByIdInstrumento(int idInstrumento);
}