package com.example.postventa.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.postventa.dto.CompraDto;
import com.example.postventa.model.PostventaModel;
import com.example.postventa.repository.PostventaRepository;

@Service
public class PostventaService {

    private static final String COMPRA_URL = "http://localhost:8083/compras/";
    private static final String COMPRA_VALIDAR_URL = "http://localhost:8083/compras/validar/";
    private static final String USUARIO_VALIDAR_URL = "http://localhost:8089/usuarios/validar/";
    private static final String DESPACHO_PEDIDO_URL = "http://localhost:8088/despachos/pedido/";

    @Autowired
    private PostventaRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public List<PostventaModel> listarPostventa() { return repository.findAll(); }

    public Optional<PostventaModel> buscarPorId(int id) { return repository.findById(id); }

    public PostventaModel crearPostventa(PostventaModel p) {
        validarExistencia(USUARIO_VALIDAR_URL + p.getIdUsuario(), "Usuario no encontrado: " + p.getIdUsuario());
        validarExistencia(COMPRA_VALIDAR_URL + p.getIdPedido(), "Compra no encontrada: " + p.getIdPedido());
        validarDespachoPrevio(p.getIdPedido());

        CompraDto compra = obtenerCompra(p.getIdPedido());
        if (compra.getIdUsuario() != p.getIdUsuario()) {
            throw new IllegalArgumentException("La compra no pertenece al usuario indicado");
        }

        if (p.getEstado() == null || p.getEstado().isBlank()) {
            p.setEstado("ABIERTO");
        }
        p.setFechaCreacion(LocalDateTime.now());

        return repository.save(p);
    }

    public PostventaModel actualizarEstado(int idTicket, String nuevoEstado) {
        Optional<PostventaModel> opt = repository.findById(idTicket);
        if (opt.isEmpty()) throw new RuntimeException("Ticket no encontrado: " + idTicket);
        PostventaModel t = opt.get();
        t.setEstado(nuevoEstado);
        return repository.save(t);
    }

    public void eliminar(int id) { repository.deleteById(id); }

    private void validarExistencia(String url, String mensaje) {
        try {
            Boolean existe = restTemplate.getForObject(url, Boolean.class);
            if (!Boolean.TRUE.equals(existe)) {
                throw new IllegalArgumentException(mensaje);
            }
        } catch (RestClientException ex) {
            throw new RuntimeException("Error validando recurso: " + ex.getMessage());
        }
    }

    private void validarDespachoPrevio(int idPedido) {
        try {
            ResponseEntity<Object> resp = restTemplate.getForEntity(DESPACHO_PEDIDO_URL + idPedido, Object.class);
            if (!resp.getStatusCode().is2xxSuccessful()) {
                throw new IllegalArgumentException("Debe existir un despacho para este pedido");
            }
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Debe existir un despacho para este pedido");
        } catch (RestClientException ex) {
            throw new RuntimeException("Error validando despacho: " + ex.getMessage());
        }
    }

    private CompraDto obtenerCompra(int idPedido) {
        try {
            CompraDto compra = restTemplate.getForObject(COMPRA_URL + idPedido, CompraDto.class);
            if (compra == null) {
                throw new IllegalArgumentException("Compra no encontrada: " + idPedido);
            }
            return compra;
        } catch (HttpClientErrorException.NotFound e) {
            throw new IllegalArgumentException("Compra no encontrada: " + idPedido);
        } catch (RestClientException ex) {
            throw new RuntimeException("Error obteniendo compra: " + ex.getMessage());
        }
    }
}
