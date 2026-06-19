package com.example.despacho.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.despacho.model.DespachoModel;

@Repository
public interface DespachoRepository extends JpaRepository<DespachoModel, Integer> {

    Optional<DespachoModel> findByIdPedido(int idPedido);
}
