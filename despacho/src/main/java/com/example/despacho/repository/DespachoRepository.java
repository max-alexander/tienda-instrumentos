package com.example.despacho.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.despacho.model.DespachoModel;

@Repository
public interface DespachoRepository extends JpaRepository<DespachoModel, Integer> {

}
