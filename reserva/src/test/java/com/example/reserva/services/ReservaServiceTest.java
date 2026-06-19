package com.example.reserva.services;

import com.example.reserva.model.Reserva;
import com.example.reserva.repository.ReservaRepository;
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
class ReservaServiceTest {

    @Mock
    private ReservaRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ReservaService service;

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva();
        reserva.setIdReserva(1);
        reserva.setIdInstrumento(10);
        reserva.setIdUsuario(2);
        reserva.setIdSucursal(1);
        reserva.setFechaVisita(LocalDate.now());
        reserva.setEstadoReserva("ACTIVA");
    }

    @Test
    void listarReservas_debeRetornarLista() {
        when(repository.findAll()).thenReturn(Arrays.asList(reserva));

        List<Reserva> resultado = service.listarReservas();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarOptional() {
        when(repository.findById(1)).thenReturn(Optional.of(reserva));

        Optional<Reserva> resultado = service.buscarPorId(1);

        assertTrue(resultado.isPresent());
    }

    @Test
    void guardarReserva_cuandoInstrumentoValido_debeGuardar() {
        when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(true);
        when(repository.save(reserva)).thenReturn(reserva);

        Reserva resultado = service.guardarReserva(reserva);

        assertNotNull(resultado);
        verify(repository).save(reserva);
    }

    @Test
    void guardarReserva_cuandoInstrumentoNoExiste_debeLanzarExcepcion() {
        when(restTemplate.getForObject(anyString(), eq(Boolean.class))).thenReturn(false);

        assertThrows(RuntimeException.class, () -> service.guardarReserva(reserva));
        verify(repository, never()).save(any());
    }

    @Test
    void eliminar_debeLlamarDeleteById() {
        service.eliminar(1);

        verify(repository).deleteById(1);
    }
}
