package com.example.compra.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.compra.model.Compra;

public interface CompraRepository
        extends JpaRepository<Compra, Integer> {

}