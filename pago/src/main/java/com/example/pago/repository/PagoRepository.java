package com.example.pago.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.pago.model.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

}