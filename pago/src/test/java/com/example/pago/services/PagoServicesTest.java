package com.example.pago.services;

import com.example.pago.model.Pago;
import com.example.pago.repository.PagoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagoServicesTest {

    @Mock
    private PagoRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PagoServices service;

    private Pago pago;

    @BeforeEach
    void setUp() {
        pago = new Pago();
        pago.setIdPago(1);
        pago.setIdCompra(5);
        pago.setMontoPago(100000);
        pago.setMetodoPago("TARJETA");
        pago.setEstadoPago("PENDIENTE");
    }

    @Test
    void listarPagos_debeRetornarLista() {
        when(repository.findAll()).thenReturn(Arrays.asList(pago));

        List<Pago> resultado = service.listarPagos();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarOptional() {
        when(repository.findById(1)).thenReturn(Optional.of(pago));

        Optional<Pago> resultado = service.buscarPorId(1);

        assertTrue(resultado.isPresent());
    }

    @Test
    void procesarPago_cuandoCompraExiste_debeGuardarPago() {
        when(restTemplate.getForObject(anyString(), eq(Object.class))).thenReturn(new Object());
        when(repository.save(pago)).thenReturn(pago);

        Pago resultado = service.procesarPago(pago);

        assertNotNull(resultado);
        verify(repository).save(pago);
    }

    @Test
    void procesarPago_cuandoCompraNoExiste_debeLanzarExcepcion() {
        when(restTemplate.getForObject(anyString(), eq(Object.class))).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.procesarPago(pago));
        assertTrue(ex.getMessage().contains("No se puede procesar el pago"));
        verify(repository, never()).save(any());
    }

    @Test
    void eliminar_debeLlamarDeleteById() {
        service.eliminar(1);

        verify(repository).deleteById(1);
    }
}
