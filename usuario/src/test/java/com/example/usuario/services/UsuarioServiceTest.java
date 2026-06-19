package com.example.usuario.services;

import com.example.usuario.model.Direccion;
import com.example.usuario.model.LoginRequest;
import com.example.usuario.model.UsuarioModel;
import com.example.usuario.repository.DireccionRepository;
import com.example.usuario.repository.RolRepository;
import com.example.usuario.repository.UsuarioRepository;
import com.example.usuario.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private DireccionRepository direccionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UsuarioService service;

    private UsuarioModel usuario;
    private Direccion direccion;
    private LoginRequest loginRequest;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioModel();
        usuario.setIdUsuario(1);
        usuario.setNombre("Juan");
        usuario.setEmail("juan@test.com");
        usuario.setPass("secret123");
        usuario.setTelefono("123456789");
        usuario.setIdRol(1);
        usuario.setIdDireccion(0);

        direccion = new Direccion();
        direccion.setIdDireccion(1);
        direccion.setIdUsuario(1);
        direccion.setCalle("Av. Principal 100");
        direccion.setCiudad("Santiago");
        direccion.setProvincia("RM");
        direccion.setCodigoPostal("8320000");
        direccion.setPais("Chile");

        loginRequest = new LoginRequest();
        loginRequest.setEmail("juan@test.com");
        loginRequest.setPass("secret123");
    }

    @Test
    void listarUsuarios_debeRetornarLista() {
        when(usuarioRepository.findAll()).thenReturn(Arrays.asList(usuario));

        List<UsuarioModel> resultado = service.listarUsuarios();

        assertEquals(1, resultado.size());
        verify(usuarioRepository).findAll();
    }

    @Test
    void buscarPorId_cuandoExiste_debeRetornarOptional() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        Optional<UsuarioModel> resultado = service.buscarPorId(1);

        assertTrue(resultado.isPresent());
    }

    @Test
    void validarUsuario_cuandoExiste_debeRetornarTrue() {
        when(usuarioRepository.existsById(1)).thenReturn(true);

        boolean resultado = service.validarUsuario(1);

        assertTrue(resultado);
    }

    @Test
    void validarDireccion_cuandoPerteneceAlUsuario_debeRetornarTrue() {
        when(direccionRepository.findById(1)).thenReturn(Optional.of(direccion));

        assertTrue(service.validarDireccion(1, 1));
    }

    @Test
    void validarDireccion_cuandoNoPerteneceAlUsuario_debeRetornarFalse() {
        when(direccionRepository.findById(1)).thenReturn(Optional.of(direccion));

        assertFalse(service.validarDireccion(1, 99));
    }

    @Test
    void crearUsuario_cuandoDatosValidos_debeGuardar() {
        when(rolRepository.existsById(1)).thenReturn(true);
        when(passwordEncoder.encode("secret123")).thenReturn("encoded");
        when(usuarioRepository.save(any(UsuarioModel.class))).thenReturn(usuario);

        UsuarioModel resultado = service.crearUsuario(usuario);

        assertNotNull(resultado);
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void actualizarUsuario_cuandoExiste_debeActualizar() {
        UsuarioModel actualizado = new UsuarioModel();
        actualizado.setNombre("Pedro");
        actualizado.setEmail("pedro@test.com");
        actualizado.setTelefono("987654321");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioModel resultado = service.actualizarUsuario(1, actualizado);

        assertNotNull(resultado);
        assertEquals("Pedro", usuario.getNombre());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void actualizarRol_cuandoExiste_debeActualizar() {
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
        when(rolRepository.existsById(2)).thenReturn(true);
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        UsuarioModel resultado = service.actualizarRol(1, 2);

        assertNotNull(resultado);
        assertEquals(2, usuario.getIdRol());
        verify(usuarioRepository).save(usuario);
    }

    @Test
    void agregarDireccion_cuandoUsuarioExiste_debeGuardar() {
        when(usuarioRepository.existsById(1)).thenReturn(true);
        when(direccionRepository.save(any(Direccion.class))).thenReturn(direccion);

        Direccion resultado = service.agregarDireccion(1, direccion);

        assertNotNull(resultado);
        assertEquals(1, direccion.getIdUsuario());
        verify(direccionRepository).save(direccion);
    }

    @Test
    void actualizarDireccion_cuandoExiste_debeActualizar() {
        Direccion actualizada = new Direccion();
        actualizada.setCalle("Nueva Calle 200");
        actualizada.setCiudad("ValparaÃ­so");
        actualizada.setProvincia("V");
        actualizada.setCodigoPostal("2340000");
        actualizada.setPais("Chile");

        when(direccionRepository.findById(1)).thenReturn(Optional.of(direccion));
        when(direccionRepository.save(direccion)).thenReturn(direccion);

        Direccion resultado = service.actualizarDireccion(1, actualizada);

        assertNotNull(resultado);
        assertEquals("Nueva Calle 200", direccion.getCalle());
        verify(direccionRepository).save(direccion);
    }

    @Test
    void eliminarUsuario_debeLlamarDeleteById() {
        service.eliminarUsuario(1);

        verify(usuarioRepository).deleteById(1);
    }

    @Test
    void login_cuandoCredencialesValidas_debeRetornarToken() {
        when(usuarioRepository.findByEmail("juan@test.com")).thenReturn(Optional.of(usuario));
        when(jwtService.generarToken(usuario)).thenReturn("token-jwt");

        String resultado = service.login(loginRequest);

        assertNotNull(resultado);
        assertEquals("token-jwt", resultado);
        verify(authenticationManager).authenticate(any());
    }
}
