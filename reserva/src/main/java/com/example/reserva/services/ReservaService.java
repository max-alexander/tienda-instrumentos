package com.example.reserva.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.reserva.model.Reserva;
import com.example.reserva.repository.ReservaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public List<Reserva> listarReservas() {
        return repository.findAll();
    }

    public Optional<Reserva> buscarPorId(int id) {
        return repository.findById(id);
    }

    public Reserva guardarReserva(Reserva reserva) {
        if (reserva == null || reserva.getIdInstrumento() == null || reserva.getIdInstrumento() <= 0) {
            throw new RuntimeException("ID de instrumento inválido");
        }

        String url = "http://localhost:8087/instrumentos/validar/" + reserva.getIdInstrumento();
        try {
            Boolean existe = restTemplate.getForObject(url, Boolean.class);
            if (Boolean.TRUE.equals(existe)) {
                return repository.save(reserva);
            } else {
                throw new RuntimeException("Instrumento no existe");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Servicio de catalogo no disponible: " + e.getMessage());
        }
    }

    public void eliminar(int id) {
        repository.deleteById(id);
    }
}
