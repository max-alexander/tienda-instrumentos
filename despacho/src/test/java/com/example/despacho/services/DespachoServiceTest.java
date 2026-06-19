package com.example.despacho.services;

import com.example.despacho.model.DespachoModel;
import com.example.despacho.repository.DespachoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DespachoServiceTest {

    @Mock
    private DespachoRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private DespachoService service;

    private DespachoModel despacho;

    @BeforeEach
    void setUp() {
        despacho = new DespachoModel();
        despacho.setIdEnvio(1);
        despacho.setIdPedido(5);
        despacho.setIdUsuario(2);
        despacho.setIdDireccion(3);
        despacho.setEmpresaTransporte("Chilexpress");
        despacho.setEstadoActual("PREPARANDO");
        despacho.setNumeroSeguimiento("ABC123");
        despacho.setFechaEstimadaEntrega(LocalDate.now().plusDays(3));
    }

    @Test
    void listarDespachos_debeRetornarLista() {
        when(repository.findAll()).thenReturn(Arrays.asList(despacho));

        List<DespachoModel> resultado = service.listarDespachos();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarOptional() {
        when(repository.findById(1)).thenReturn(Optional.of(despacho));

        Optional<DespachoModel> resultado = service.buscarPorId(1);

        assertTrue(resultado.isPresent());
    }

    @Test
    void crearDespacho_cuandoValidacionesOk_debeGuardar() {
        when(restTemplate.getForEntity(anyString(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("ok", HttpStatus.OK));
        when(repository.save(despacho)).thenReturn(despacho);

        DespachoModel resultado = service.crearDespacho(despacho);

        assertNotNull(resultado);
        verify(repository).save(despacho);
    }

    @Test
    void actualizarEstado_cuandoExiste_debeActualizar() {
        when(repository.findById(1)).thenReturn(Optional.of(despacho));
        when(repository.save(despacho)).thenReturn(despacho);

        DespachoModel resultado = service.actualizarEstado(1, "EN_TRANSITO");

        assertNotNull(resultado);
        assertEquals("EN_TRANSITO", despacho.getEstadoActual());
        verify(repository).save(despacho);
    }

    @Test
    void actualizarEstado_cuandoNoExiste_debeLanzarExcepcion() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.actualizarEstado(99, "EN_TRANSITO"));
    }

    @Test
    void eliminar_debeLlamarDeleteById() {
        service.eliminar(1);

        verify(repository).deleteById(1);
    }
}
