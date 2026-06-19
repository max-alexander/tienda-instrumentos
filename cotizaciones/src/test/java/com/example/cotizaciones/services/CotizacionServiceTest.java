package com.example.cotizaciones.services;

import com.example.cotizaciones.model.Cotizacion;
import com.example.cotizaciones.model.DetalleCotizacion;
import com.example.cotizaciones.repository.CotizacionRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CotizacionServiceTest {

    @Mock
    private CotizacionRepository cotizacionRepository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CotizacionService service;

    private Cotizacion cotizacion;
    private DetalleCotizacion detalle;

    @BeforeEach
    void setUp() {
        detalle = new DetalleCotizacion();
        detalle.setIdInstrumento(1);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(50000);
        detalle.setSubtotal(100000);

        cotizacion = new Cotizacion();
        cotizacion.setIdCotizacion(1);
        cotizacion.setIdCliente(5);
        cotizacion.setDetalles(Arrays.asList(detalle));
    }

    @Test
    void crearCotizacion_cuandoValida_debeGuardar() {
        when(restTemplate.getForObject(contains("/usuarios/validar/"), eq(Boolean.class))).thenReturn(true);
        when(restTemplate.getForObject(contains("/instrumentos/validar/"), eq(Boolean.class))).thenReturn(true);
        when(cotizacionRepository.save(any(Cotizacion.class))).thenReturn(cotizacion);

        Cotizacion resultado = service.crearCotizacion(cotizacion);

        assertNotNull(resultado);
        assertEquals(100000, cotizacion.getMontoTotal());
        verify(cotizacionRepository).save(cotizacion);
    }

    @Test
    void crearCotizacion_cuandoClienteInvalido_debeLanzarExcepcion() {
        cotizacion.setIdCliente(0);

        assertThrows(IllegalArgumentException.class, () -> service.crearCotizacion(cotizacion));
        verify(cotizacionRepository, never()).save(any());
    }

    @Test
    void crearCotizacion_sinDetalles_debeLanzarExcepcion() {
        cotizacion.setDetalles(null);

        assertThrows(IllegalArgumentException.class, () -> service.crearCotizacion(cotizacion));
    }

    @Test
    void obtener_cuandoExiste_debeRetornarOptional() {
        when(cotizacionRepository.findById(1)).thenReturn(Optional.of(cotizacion));

        Optional<Cotizacion> resultado = service.obtener(1);

        assertTrue(resultado.isPresent());
    }

    @Test
    void listar_debeRetornarLista() {
        when(cotizacionRepository.findAll()).thenReturn(Arrays.asList(cotizacion));

        List<Cotizacion> resultado = service.listar();

        assertEquals(1, resultado.size());
        verify(cotizacionRepository).findAll();
    }

    @Test
    void actualizarEstado_cuandoExiste_debeActualizar() {
        when(cotizacionRepository.findById(1)).thenReturn(Optional.of(cotizacion));
        when(cotizacionRepository.save(cotizacion)).thenReturn(cotizacion);

        Cotizacion resultado = service.actualizarEstado(1, "APROBADA");

        assertNotNull(resultado);
        assertEquals("APROBADA", cotizacion.getEstado());
        verify(cotizacionRepository).save(cotizacion);
    }

    @Test
    void agregarDetalle_cuandoValido_debeAgregarYGuardar() {
        DetalleCotizacion nuevoDetalle = new DetalleCotizacion();
        nuevoDetalle.setIdInstrumento(2);
        nuevoDetalle.setCantidad(1);
        nuevoDetalle.setPrecioUnitario(30000);

        cotizacion.setMontoTotal(100000);
        when(cotizacionRepository.findById(1)).thenReturn(Optional.of(cotizacion));
        when(cotizacionRepository.save(cotizacion)).thenReturn(cotizacion);

        Cotizacion resultado = service.agregarDetalle(1, nuevoDetalle);

        assertNotNull(resultado);
        assertEquals(130000, cotizacion.getMontoTotal());
        verify(cotizacionRepository).save(cotizacion);
    }
}
