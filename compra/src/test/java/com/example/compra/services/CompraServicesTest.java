package com.example.compra.services;

import com.example.compra.model.Compra;
import com.example.compra.repository.CompraRepository;
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
class CompraServicesTest {

    @Mock
    private CompraRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CompraServices service;

    private Compra compra;

    @BeforeEach
    void setUp() {
        compra = new Compra();
        compra.setIdCompra(1);
        compra.setIdInstrumento(10);
        compra.setCantidad(2);
        compra.setMontoTotal(100000);
        compra.setMetodoPago("TARJETA");
    }

    @Test
    void listarCompras_debeRetornarLista() {
        when(repository.findAll()).thenReturn(Arrays.asList(compra));

        List<Compra> resultado = service.listarCompras();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarOptional() {
        when(repository.findById(1)).thenReturn(Optional.of(compra));

        Optional<Compra> resultado = service.buscarPorId(1);

        assertTrue(resultado.isPresent());
    }

    @Test
    void realizarCompra_cuandoTodoOk_debeCompletarCompra() {
        when(restTemplate.getForObject(contains("/instrumentos/validar/"), eq(Boolean.class))).thenReturn(true);
        when(restTemplate.getForObject(contains("/inventario/instrumento/"), eq(Object.class))).thenReturn(new Object());

        Compra compraPendiente = new Compra();
        compraPendiente.setIdCompra(1);
        compraPendiente.setIdInstrumento(10);
        compraPendiente.setEstadoCompra("PENDIENTE");

        Compra compraCompletada = new Compra();
        compraCompletada.setIdCompra(1);
        compraCompletada.setEstadoCompra("COMPLETADA");

        when(repository.save(any(Compra.class)))
                .thenReturn(compraPendiente)
                .thenReturn(compraCompletada);
        when(restTemplate.postForObject(anyString(), any(), eq(Object.class))).thenReturn(new Object());

        Compra resultado = service.realizarCompra(compra);

        assertNotNull(resultado);
        assertEquals("COMPLETADA", resultado.getEstadoCompra());
        verify(repository, times(2)).save(any(Compra.class));
    }

    @Test
    void eliminar_debeLlamarDeleteById() {
        service.eliminar(1);

        verify(repository).deleteById(1);
    }
}
