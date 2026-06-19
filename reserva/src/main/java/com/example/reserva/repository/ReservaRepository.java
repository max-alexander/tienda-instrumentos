package com.example.reserva.repository;


import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.reserva.model.Reserva; 

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    Optional<Reserva> findByIdInstrumento(Integer idInstrumento);
}