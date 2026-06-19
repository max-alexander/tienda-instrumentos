package com.example.inventario.services;

import com.example.inventario.model.Inventario;
import com.example.inventario.repository.InventarioRepository;
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
class InventarioServiceTest {

    @Mock
    private InventarioRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private InventarioService service;

    private Inventario inventario;

    @BeforeEach
    void setUp() {
        inventario = new Inventario();
        inventario.setIdInventario(1);
        inventario.setIdInstrumento(10);
        inventario.setStockInstrumento(5);
        inventario.setSucursal("Santiago");
    }

    @Test
    void listarInventario_debeRetornarLista() {
        when(repository.findAll()).thenReturn(Arrays.asList(inventario));

        List<Inventario> resultado = service.listarInventario();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarOptional() {
        when(repository.findById(1)).thenReturn(Optional.of(inventario));

        Optional<Inventario> resultado = service.buscarPorId(1);

        assertTrue(resultado.isPresent());
    }

    @Test
    void buscarPorInstrumento_debeRetornarOptional() {
        when(repository.findByIdInstrumento(10)).thenReturn(Optional.of(inventario));

        Optional<Inventario> resultado = service.buscarPorInstrumento(10);

        assertTrue(resultado.isPresent());
        assertEquals(10, resultado.get().getIdInstrumento());
    }

    @Test
    void actualizarStock_cuandoExiste_debeActualizar() {
        when(repository.findById(1)).thenReturn(Optional.of(inventario));
        when(repository.save(inventario)).thenReturn(inventario);

        Inventario resultado = service.actualizarStock(1, 20);

        assertNotNull(resultado);
        assertEquals(20, inventario.getStockInstrumento());
        verify(repository).save(inventario);
    }

    @Test
    void actualizarStock_cuandoNoExiste_debeRetornarNull() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        Inventario resultado = service.actualizarStock(99, 20);

        assertNull(resultado);
    }

    @Test
    void guardarInventario_cuandoInstrumentoValido_debeGuardar() {
        when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(true);
        when(repository.save(inventario)).thenReturn(inventario);

        Inventario resultado = service.guardarInventario(inventario);

        assertNotNull(resultado);
        verify(repository).save(inventario);
    }

    @Test
    void guardarInventario_cuandoInstrumentoNoExiste_debeLanzarExcepcion() {
        when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.guardarInventario(inventario));
        verify(repository, never()).save(any());
    }

    @Test
    void eliminar_debeLlamarDeleteById() {
        service.eliminar(1);

        verify(repository).deleteById(1);
    }
}
