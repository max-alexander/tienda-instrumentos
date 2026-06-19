package com.example.usuario.service;

import com.example.usuario.model.UsuarioModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private UsuarioModel usuario;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtService, "secret",
                "testSecretKeyThatIsLongEnoughForHmacSha256Algorithm");
        usuario = new UsuarioModel();
        usuario.setIdUsuario(1);
        usuario.setEmail("juan@test.com");
        usuario.setNombre("Juan");
    }

    @Test
    void generarToken_debeRetornarStringNoNulo() {
        String token = jwtService.generarToken(usuario);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
}
