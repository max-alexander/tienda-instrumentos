package com.example.proyecto_resena.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.proyecto_resena.dto.CompraDto;
import com.example.proyecto_resena.model.Resenas;
import com.example.proyecto_resena.repository.ResenasRepository;

@Service
public class ResenasService {

    private static final String USUARIO_VALIDAR_URL = "http://localhost:8089/usuarios/validar/";
    private static final String INSTRUMENTO_VALIDAR_URL = "http://localhost:8087/instrumentos/validar/";
    private static final String COMPRA_VALIDAR_URL = "http://localhost:8083/compras/validar/";
    private static final String COMPRA_URL = "http://localhost:8083/compras/";

    @Autowired
    private ResenasRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    public List<Resenas> listarTodos(){
        return repo.findAll();
    }

    public Optional<Resenas> buscarPorId(int id){
        return repo.findById(id);
    }

    public Resenas gurardar(Resenas r){
        if (r.getEstrellas() == null || r.getEstrellas() < 1 || r.getEstrellas() > 5) {
            throw new IllegalArgumentException("Las estrellas deben estar entre 1 y 5");
        }
        if (r.getIdUsuario() == null || r.getIdUsuario() <= 0) {
            throw new IllegalArgumentException("idUsuario es obligatorio");
        }
        if (r.getIdInstrumento() == null || r.getIdInstrumento() <= 0) {
            throw new IllegalArgumentException("idInstrumento es obligatorio");
        }
        if (r.getIdCompra() == null || r.getIdCompra() <= 0) {
            throw new IllegalArgumentException("idCompra es obligatorio");
        }

        validarExistencia(USUARIO_VALIDAR_URL + r.getIdUsuario(), "Usuario no existe");
        validarExistencia(INSTRUMENTO_VALIDAR_URL + r.getIdInstrumento(), "Instrumento no existe");
        validarExistencia(COMPRA_VALIDAR_URL + r.getIdCompra(), "Compra no existe");

        CompraDto compra = obtenerCompra(r.getIdCompra());
        if (compra.getIdUsuario() != r.getIdUsuario()) {
            throw new IllegalArgumentException("La compra no pertenece al usuario indicado");
        }
        if (compra.getIdInstrumento() != r.getIdInstrumento()) {
            throw new IllegalArgumentException("El instrumento no coincide con la compra");
        }
        if (!"COMPLETADA".equals(compra.getEstadoCompra())) {
            throw new IllegalArgumentException("Solo se pueden reseñar compras COMPLETADAS");
        }

        r.setFecha(LocalDate.now());
        return repo.save(r);
    }

    private void validarExistencia(String url, String mensaje) {
        try {
            Boolean existe = restTemplate.getForObject(url, Boolean.class);
            if (!Boolean.TRUE.equals(existe)) {
                throw new IllegalArgumentException(mensaje);
            }
        } catch (RestClientException e) {
            throw new IllegalArgumentException("Servicio externo no disponible: " + e.getMessage());
        }
    }

    private CompraDto obtenerCompra(int idCompra) {
        try {
            CompraDto compra = restTemplate.getForObject(COMPRA_URL + idCompra, CompraDto.class);
            if (compra == null) {
                throw new IllegalArgumentException("Compra no encontrada: " + idCompra);
            }
            return compra;
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Compra no encontrada: " + idCompra);
        } catch (RestClientException e) {
            throw new IllegalArgumentException("Error obteniendo compra: " + e.getMessage());
        }
    }
}
