package com.example.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.model.Instrumento;
import com.example.repository.InstrumentoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InstrumentoService {

    @Autowired
    private InstrumentoRepository repository;

    public List<Instrumento> listarTodos() {
        return repository.findAll();
    }

    public Optional<Instrumento> buscarPorId(int id) {
        return repository.findById(id);
    }

    public List<Instrumento> buscarPorMarca(String marca) {
        return repository.findByMarcaInstrumento(marca);
    }

    public List<Instrumento> buscarPorTipo(String tipo) {
        return repository.findByTipoInstrumento(tipo);
    }

    public Instrumento guardar(Instrumento instrumento) {
    return repository.save(instrumento);
    }

    public Instrumento actualizar(int id, Instrumento instrumentoActualizado) {

        Instrumento instrumento = repository.findById(id).orElse(null);

        if (instrumento != null) {

            instrumento.setNombreInstrumento(instrumentoActualizado.getNombreInstrumento());
            instrumento.setMarcaInstrumento(instrumentoActualizado.getMarcaInstrumento());
            instrumento.setTipoInstrumento(instrumentoActualizado.getTipoInstrumento());
            instrumento.setPrecioInstrumento(instrumentoActualizado.getPrecioInstrumento());
            instrumento.setDescripcionInstrumento(instrumentoActualizado.getDescripcionInstrumento());
            

            return repository.save(instrumento);
        }

        return null;
    }

    public void eliminar(int id) {
        repository.deleteById(id);
    }

    public boolean validarInstrumento(int id) {
        return repository.findById(id).isPresent();
    }
}

