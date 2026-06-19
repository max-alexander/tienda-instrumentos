package com.example.proyecto_resena.service;

import com.example.proyecto_resena.dto.CompraDto;
import com.example.proyecto_resena.model.Resenas;
import com.example.proyecto_resena.repository.ResenasRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResenasServiceTest {

    @Mock
    private ResenasRepository repo;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ResenasService service;

    private Resenas resena;
    private CompraDto compraDto;

    @BeforeEach
    void setUp() {
        resena = new Resenas();
        resena.setId(1);
        resena.setEstrellas(5);
        resena.setComentarios("Excelente producto");
        resena.setIdUsuario(2);
        resena.setIdInstrumento(10);
        resena.setIdCompra(5);

        compraDto = new CompraDto();
        compraDto.setIdCompra(5);
        compraDto.setIdUsuario(2);
        compraDto.setIdInstrumento(10);
        compraDto.setEstadoCompra("COMPLETADA");
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
        when(restTemplate.getForObject(contains("/validar/"), eq(Boolean.class))).thenReturn(true);
        when(restTemplate.getForObject(contains("/compras/5"), eq(CompraDto.class))).thenReturn(compraDto);
        when(repo.save(resena)).thenReturn(resena);

        Resenas resultado = service.gurardar(resena);

        assertNotNull(resultado);
        assertEquals(LocalDate.now(), resena.getFecha());
        verify(repo).save(resena);
    }

    @Test
    void gurardar_cuandoInstrumentoNoCoincide_debeLanzarExcepcion() {
        compraDto.setIdInstrumento(99);
        when(restTemplate.getForObject(contains("/validar/"), eq(Boolean.class))).thenReturn(true);
        when(restTemplate.getForObject(contains("/compras/5"), eq(CompraDto.class))).thenReturn(compraDto);

        assertThrows(IllegalArgumentException.class, () -> service.gurardar(resena));
        verify(repo, never()).save(any());
    }
}
