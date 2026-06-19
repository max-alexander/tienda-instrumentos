package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Instrumento;
import java.util.List;

public interface InstrumentoRepository extends JpaRepository<Instrumento, Integer> {
    List<Instrumento> findByMarcaInstrumento(String marca);
    List<Instrumento> findByTipoInstrumento(String tipo);
}