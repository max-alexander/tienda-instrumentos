package com.example.postventa.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.postventa.model.PostventaModel;
import com.example.postventa.repository.PostventaRepository;

@Service
public class PostventaService {

    @Autowired
    private PostventaRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String COMPRA_URL = "http://localhost:8083/compras/";
    private final String USUARIO_URL = "http://localhost:8089/usuarios/";

    public List<PostventaModel> listarPostventa() { return repository.findAll(); }

    public Optional<PostventaModel> buscarPorId(int id) { return repository.findById(id); }

    public PostventaModel crearPostventa(PostventaModel p) {
        try {
            ResponseEntity<String> compraResp = restTemplate.getForEntity(COMPRA_URL + p.getIdPedido(), String.class);
            if (!compraResp.getStatusCode().is2xxSuccessful()) {
                throw new IllegalArgumentException("Compra no encontrada: " + p.getIdPedido());
            }
        } catch (RestClientException ex) {
            throw new RuntimeException("Error validando compra: " + ex.getMessage());
        }

        try {
            ResponseEntity<String> userResp = restTemplate.getForEntity(USUARIO_URL + p.getIdUsuario(), String.class);
            if (!userResp.getStatusCode().is2xxSuccessful()) {
                throw new IllegalArgumentException("Usuario no encontrado: " + p.getIdUsuario());
            }
        } catch (RestClientException ex) {
            throw new RuntimeException("Error validando usuario: " + ex.getMessage());
        }

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
}
