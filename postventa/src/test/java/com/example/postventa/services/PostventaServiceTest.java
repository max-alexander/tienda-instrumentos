package com.example.postventa.services;

import com.example.postventa.dto.CompraDto;
import com.example.postventa.model.PostventaModel;
import com.example.postventa.repository.PostventaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostventaServiceTest {

    @Mock
    private PostventaRepository repository;

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private PostventaService service;

    private PostventaModel postventa;
    private CompraDto compraDto;

    @BeforeEach
    void setUp() {
        postventa = new PostventaModel();
        postventa.setIdTicket(1);
        postventa.setIdUsuario(2);
        postventa.setIdPedido(5);
        postventa.setTipoSolicitud("GARANTIA");
        postventa.setDescripcion("Producto defectuoso");
        postventa.setEstado("ABIERTO");

        compraDto = new CompraDto();
        compraDto.setIdCompra(5);
        compraDto.setIdUsuario(2);
    }

    @Test
    void listarPostventa_debeRetornarLista() {
        when(repository.findAll()).thenReturn(Arrays.asList(postventa));

        List<PostventaModel> resultado = service.listarPostventa();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarOptional() {
        when(repository.findById(1)).thenReturn(Optional.of(postventa));

        Optional<PostventaModel> resultado = service.buscarPorId(1);

        assertTrue(resultado.isPresent());
    }

    @Test
    void crearPostventa_cuandoValidacionesOk_debeGuardar() {
        when(restTemplate.getForObject(contains("/validar/"), eq(Boolean.class))).thenReturn(true);
        when(restTemplate.getForEntity(contains("/despachos/pedido/"), eq(Object.class)))
                .thenReturn(new ResponseEntity<>(new Object(), HttpStatus.OK));
        when(restTemplate.getForObject(contains("/compras/5"), eq(CompraDto.class))).thenReturn(compraDto);
        when(repository.save(postventa)).thenReturn(postventa);

        PostventaModel resultado = service.crearPostventa(postventa);

        assertNotNull(resultado);
        assertNotNull(postventa.getFechaCreacion());
        verify(repository).save(postventa);
    }

    @Test
    void actualizarEstado_cuandoExiste_debeActualizar() {
        when(repository.findById(1)).thenReturn(Optional.of(postventa));
        when(repository.save(postventa)).thenReturn(postventa);

        PostventaModel resultado = service.actualizarEstado(1, "CERRADO");

        assertNotNull(resultado);
        assertEquals("CERRADO", postventa.getEstado());
        verify(repository).save(postventa);
    }

    @Test
    void actualizarEstado_cuandoNoExiste_debeLanzarExcepcion() {
        when(repository.findById(99)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.actualizarEstado(99, "CERRADO"));
    }

    @Test
    void eliminar_debeLlamarDeleteById() {
        service.eliminar(1);

        verify(repository).deleteById(1);
    }
}
