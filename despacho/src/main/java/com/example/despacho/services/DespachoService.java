package com.example.despacho.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.despacho.dto.CompraDto;
import com.example.despacho.model.DespachoModel;
import com.example.despacho.repository.DespachoRepository;

@Service
public class DespachoService {

    private static final String COMPRA_URL = "http://localhost:8083/compras/";
    private static final String COMPRA_VALIDAR_URL = "http://localhost:8083/compras/validar/";
    private static final String USUARIO_VALIDAR_URL = "http://localhost:8089/usuarios/validar/";
    private static final String DIRECCION_VALIDAR_URL = "http://localhost:8089/direcciones/validar/";

    @Autowired
    private DespachoRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public List<DespachoModel> listarDespachos() { return repository.findAll(); }

    public Optional<DespachoModel> buscarPorId(int id) { return repository.findById(id); }

    public Optional<DespachoModel> buscarPorPedido(int idPedido) {
        return repository.findByIdPedido(idPedido);
    }

    public DespachoModel crearDespacho(DespachoModel d) {
        validarExistencia(USUARIO_VALIDAR_URL + d.getIdUsuario(), "Usuario no encontrado: " + d.getIdUsuario());
        validarExistencia(COMPRA_VALIDAR_URL + d.getIdPedido(), "Compra no encontrada: " + d.getIdPedido());
        validarExistencia(
                DIRECCION_VALIDAR_URL + d.getIdDireccion() + "?idUsuario=" + d.getIdUsuario(),
                "Direccion invalida para el usuario: " + d.getIdDireccion());

        CompraDto compra = obtenerCompra(d.getIdPedido());
        if (compra.getIdUsuario() != d.getIdUsuario()) {
            throw new RuntimeException("La compra no pertenece al usuario indicado");
        }
        if (!"COMPLETADA".equals(compra.getEstadoCompra())) {
            throw new RuntimeException("La compra debe estar COMPLETADA para crear despacho");
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

    private void validarExistencia(String url, String mensaje) {
        try {
            Boolean existe = restTemplate.getForObject(url, Boolean.class);
            if (!Boolean.TRUE.equals(existe)) {
                throw new RuntimeException(mensaje);
            }
        } catch (RestClientException ex) {
            throw new RuntimeException("Error validando recurso: " + ex.getMessage());
        }
    }

    private CompraDto obtenerCompra(int idPedido) {
        try {
            CompraDto compra = restTemplate.getForObject(COMPRA_URL + idPedido, CompraDto.class);
            if (compra == null) {
                throw new RuntimeException("Compra no encontrada: " + idPedido);
            }
            return compra;
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("Compra no encontrada: " + idPedido);
        } catch (RestClientException ex) {
            throw new RuntimeException("Error obteniendo compra: " + ex.getMessage());
        }
    }
}
