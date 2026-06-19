package com.example.despacho.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.despacho.model.DespachoModel;
import com.example.despacho.repository.DespachoRepository;

@Service
public class DespachoService {

    @Autowired
    private DespachoRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String COMPRA_URL = "http://localhost:8083/compras/";
    private final String USUARIO_URL = "http://localhost:8089/usuarios/";

    public List<DespachoModel> listarDespachos() { return repository.findAll(); }

    public Optional<DespachoModel> buscarPorId(int id) { return repository.findById(id); }

    public DespachoModel crearDespacho(DespachoModel d) {
        try {
            ResponseEntity<String> compraResp = restTemplate.getForEntity(COMPRA_URL + d.getIdPedido(), String.class);
            if (!compraResp.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Compra no encontrada: " + d.getIdPedido());
            }
        } catch (RestClientException ex) {
            throw new RuntimeException("Error validando compra: " + ex.getMessage());
        }

        try {
            ResponseEntity<String> userResp = restTemplate.getForEntity(USUARIO_URL + d.getIdUsuario(), String.class);
            if (!userResp.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Usuario no encontrado: " + d.getIdUsuario());
            }
        } catch (RestClientException ex) {
            throw new RuntimeException("Error validando usuario: " + ex.getMessage());
        }

        return repository.save(d);
    }

    public DespachoModel actualizarEstado(int idEnvio, String nuevoEstado) {
        Optional<DespachoModel> opt = repository.findById(idEnvio);
        if (opt.isEmpty()) throw new RuntimeException("Envio no encontrado: " + idEnvio);
        DespachoModel e = opt.get();
        e.setEstadoActual(nuevoEstado);
        return repository.save(e);
    }

    public void eliminar(int id) { repository.deleteById(id); }
}
