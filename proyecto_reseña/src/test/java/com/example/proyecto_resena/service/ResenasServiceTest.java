package com.example.proyecto_resena.service;

import com.example.proyecto_resena.model.Resenas;
import com.example.proyecto_resena.repository.ResenasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResenasServiceTest {

    @Mock
    private ResenasRepository repo;

    @InjectMocks
    private ResenasService service;

    private Resenas resena;

    @BeforeEach
    void setUp() {
        resena = new Resenas();
        resena.setId(1);
        resena.setEstrellas(5);
        resena.setComentarios("Excelente producto");
        resena.setIdUsuario(2);
    }

    @Test
    void listarTodos_debeRetornarLista() {
        when(repo.findAll()).thenReturn(Arrays.asList(resena));

        List<Resenas> resultado = service.listarTodos();

        assertEquals(1, resultado.size());
        verify(repo).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarOptional() {
        when(repo.findById(1)).thenReturn(Optional.of(resena));

        Optional<Resenas> resultado = service.buscarPorId(1);

        assertTrue(resultado.isPresent());
    }

    @Test
    void gurardar_debeEstablecerFechaYGuardar() {
        when(repo.save(resena)).thenReturn(resena);

        Resenas resultado = service.gurardar(resena);

        assertNotNull(resultado);
        assertEquals(LocalDate.now(), resena.getFecha());
        verify(repo).save(resena);
    }
}
