package com.example.services;

import com.example.model.Instrumento;
import com.example.repository.InstrumentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstrumentoServiceTest {

    @Mock
    private InstrumentoRepository repository;

    @InjectMocks
    private InstrumentoService service;

    private Instrumento instrumento;

    @BeforeEach
    void setUp() {
        instrumento = new Instrumento();
        instrumento.setIdInstrumento(1);
        instrumento.setNombreInstrumento("Guitarra");
        instrumento.setMarcaInstrumento("Fender");
        instrumento.setTipoInstrumento("Cuerda");
        instrumento.setPrecioInstrumento(500000);
        instrumento.setDescripcionInstrumento("Guitarra elÃ©ctrica");
    }

    @Test
    void listarTodos_debeRetornarLista() {
        when(repository.findAll()).thenReturn(Arrays.asList(instrumento));

        List<Instrumento> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarOptional() {
        when(repository.findById(1)).thenReturn(Optional.of(instrumento));

        Optional<Instrumento> resultado = service.buscarPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Guitarra", resultado.get().getNombreInstrumento());
    }

    @Test
    void buscarPorMarca_debeRetornarLista() {
        when(repository.findByMarcaInstrumento("Fender")).thenReturn(Arrays.asList(instrumento));

        List<Instrumento> resultado = service.buscarPorMarca("Fender");

        assertEquals(1, resultado.size());
        verify(repository).findByMarcaInstrumento("Fender");
    }

    @Test
    void buscarPorTipo_debeRetornarLista() {
        when(repository.findByTipoInstrumento("Cuerda")).thenReturn(Arrays.asList(instrumento));

        List<Instrumento> resultado = service.buscarPorTipo("Cuerda");

        assertEquals(1, resultado.size());
        verify(repository).findByTipoInstrumento("Cuerda");
    }

    @Test
    void guardar_debePersistirInstrumento() {
        when(repository.save(instrumento)).thenReturn(instrumento);

        Instrumento resultado = service.guardar(instrumento);

        assertNotNull(resultado);
        verify(repository).save(instrumento);
    }

    @Test
    void actualizar_cuandoExiste_debeActualizarYGuardar() {
        Instrumento actualizado = new Instrumento();
        actualizado.setNombreInstrumento("Bajo");
        actualizado.setMarcaInstrumento("Yamaha");
        actualizado.setTipoInstrumento("Cuerda");
        actualizado.setPrecioInstrumento(300000);
        actualizado.setDescripcionInstrumento("Bajo elÃ©ctrico");

        when(repository.findById(1)).thenReturn(Optional.of(instrumento));
        when(repository.save(any(Instrumento.class))).thenReturn(instrumento);

        Instrumento resultado = service.actualizar(1, actualizado);

        assertNotNull(resultado);
        assertEquals("Bajo", instrumento.getNombreInstrumento());
        verify(repository).save(instrumento);
    }

    @Test
    void actualizar_cuandoNoExiste_debeRetornarNull() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        Instrumento resultado = service.actualizar(99, instrumento);

        assertNull(resultado);
        verify(repository, never()).save(any());
    }

    @Test
    void eliminar_debeLlamarDeleteById() {
        service.eliminar(1);

        verify(repository).deleteById(1);
    }

    @Test
    void validarInstrumento_cuandoExiste_debeRetornarTrue() {
        when(repository.findById(1)).thenReturn(Optional.of(instrumento));

        boolean resultado = service.validarInstrumento(1);

        assertTrue(resultado);
    }

    @Test
    void validarInstrumento_cuandoNoExiste_debeRetornarFalse() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        boolean resultado = service.validarInstrumento(99);

        assertFalse(resultado);
    }
}
