package com.example.pago.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.pago.model.Pago;
import com.example.pago.repository.PagoRepository;

@Service
public class PagoServices {

    @Autowired
    private PagoRepository repository;

    public List<Pago> listarPagos() {
        return repository.findAll();
    }

    @Autowired 
    private RestTemplate restTemplate;

    public Optional<Pago> buscarPorId(int id) {
        return repository.findById(id);
    }


    public Pago procesarPago(Pago pago) {
    String url = "http://localhost:8083/compras/" + pago.getIdCompra();
    try {
        Object compra = restTemplate.getForObject(url, Object.class);
        
        if (compra == null) {
            throw new RuntimeException("La compra con ID " + pago.getIdCompra() + " no existe.");
        }
        
        return repository.save(pago); 
    } catch (Exception e) {
        throw new RuntimeException("No se puede procesar el pago: " + e.getMessage());
    }
}

    public void eliminar(int id) {
        repository.deleteById(id);
    }

}
