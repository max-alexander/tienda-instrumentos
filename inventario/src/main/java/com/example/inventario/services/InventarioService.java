package com.example.inventario.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.inventario.model.Inventario;
import com.example.inventario.repository.InventarioRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InventarioService {

    @Autowired
    private InventarioRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Inventario> listarInventario() {
        return repository.findAll();
    }

    public Optional<Inventario> buscarPorId(int id) {
        return repository.findById(id);
    }

    public Optional<Inventario> buscarPorInstrumento(int idInstrumento) {
        return repository.findByIdInstrumento(idInstrumento);
    }

    public Inventario actualizarStock(int id, int nuevoStock) {
        Inventario inventario = repository.findById(id).orElse(null);

        if (inventario != null) {
            inventario.setStockInstrumento(nuevoStock);
            return repository.save(inventario);
        }
        return null;
    }
    public void eliminar(int id) {
        repository.deleteById(id);
    }

   public Inventario guardarInventario(Inventario inventario) {
    String url = "http://localhost:8087/instrumentos/validar/"
            + inventario.getIdInstrumento();
    Boolean respuesta = restTemplate.getForObject(url, Boolean.class);
    if(Boolean.TRUE.equals(respuesta)) {
        return repository.save(inventario);
    } else {
        throw new RuntimeException("Instrumento no existe");
    }
}
}
