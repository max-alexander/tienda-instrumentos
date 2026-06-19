package com.example.compra.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.compra.model.Compra;
import com.example.compra.repository.CompraRepository;

@Service
public class CompraServices {

    @Autowired
    private CompraRepository repository;
    @Autowired
    private RestTemplate restTemplate;

    public List<Compra> listarCompras() {
        return repository.findAll();
    }
    public Optional<Compra> buscarPorId(int id) {
        return repository.findById(id);
    }

    public boolean validarCompra(int id) {
        return repository.existsById(id);
    }

    public Compra realizarCompra(Compra compra) {
        if (compra.getIdUsuario() <= 0) {
            throw new RuntimeException("idUsuario es obligatorio");
        }

        String validarUsuarioURL = "http://localhost:8089/usuarios/validar/" + compra.getIdUsuario();
        Boolean usuarioExiste = restTemplate.getForObject(validarUsuarioURL, Boolean.class);
        if (Boolean.FALSE.equals(usuarioExiste)) {
            throw new RuntimeException("Usuario no existe");
        }

        String validarInstrumentoURL = "http://localhost:8087/instrumentos/validar/" + compra.getIdInstrumento();
        Boolean instrumentoExiste = restTemplate.getForObject( validarInstrumentoURL,Boolean.class);
        if(Boolean.FALSE.equals(instrumentoExiste)) {
            throw new RuntimeException("Instrumento no existe");
        }

        String inventarioURL = "http://localhost:8082/inventario/instrumento/"+ compra.getIdInstrumento();

        Object inventario = restTemplate.getForObject( inventarioURL, Object.class );

        if(inventario == null) {
            throw new RuntimeException("No hay stock");
        }

        compra.setEstadoCompra("PENDIENTE");
        Compra compraGuardada = repository.save(compra);

        String pagoURL = "http://localhost:8084/pagos";

        Object respuestaPago =restTemplate.postForObject(pagoURL,compraGuardada,Object.class);

        if(respuestaPago != null) {

            compraGuardada.setEstadoCompra("COMPLETADA");

            return repository.save(compraGuardada);

        } else {

            compraGuardada.setEstadoCompra("RECHAZADA");

            return repository.save(compraGuardada);
        }
    }

    public void eliminar(int id) {
        repository.deleteById(id);
    }
}
