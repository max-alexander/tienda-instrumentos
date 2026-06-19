package com.example.usuario.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.usuario.model.Direccion;
import com.example.usuario.model.LoginRequest;
import com.example.usuario.model.UsuarioModel;
import com.example.usuario.repository.DireccionRepository;
import com.example.usuario.repository.RolRepository;
import com.example.usuario.repository.UsuarioRepository;
import com.example.usuario.service.JwtService;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public List<UsuarioModel> listarUsuarios() { return usuarioRepository.findAll(); }

    public Optional<UsuarioModel> buscarPorId(int id) { return usuarioRepository.findById(id); }

    public boolean validarUsuario(int id) {
        return usuarioRepository.existsById(id);
    }

    public boolean validarDireccion(int idDireccion, int idUsuario) {
        return direccionRepository.findById(idDireccion)
                .map(d -> d.getIdUsuario() == idUsuario)
                .orElse(false);
    }

    public String login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPass())
        );
        UsuarioModel usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return jwtService.generarToken(usuario);
    }

    public UsuarioModel crearUsuario(UsuarioModel u) {
        if (u.getEmail() == null || u.getEmail().isEmpty()) throw new RuntimeException("Email invalido");
        if (u.getPass() == null || u.getPass().isEmpty()) throw new RuntimeException("Password invalido");
        if (u.getIdRol() != 0 && !rolRepository.existsById(u.getIdRol())) throw new RuntimeException("Rol no encontrado: " + u.getIdRol());
        if (u.getIdDireccion() != 0 && !direccionRepository.existsById(u.getIdDireccion())) throw new RuntimeException("Direccion no encontrada: " + u.getIdDireccion());
        u.setPass(passwordEncoder.encode(u.getPass()));
        return usuarioRepository.save(u);
    }

    public UsuarioModel actualizarUsuario(int id, UsuarioModel u) {
        Optional<UsuarioModel> opt = usuarioRepository.findById(id);
        if (opt.isEmpty()) throw new RuntimeException("Usuario no encontrado: " + id);
        UsuarioModel ex = opt.get();
        ex.setNombre(u.getNombre());
        ex.setEmail(u.getEmail());
        ex.setTelefono(u.getTelefono());
        return usuarioRepository.save(ex);
    }

    public UsuarioModel actualizarRol(int idUsuario, int idRol) {
        Optional<UsuarioModel> opt = usuarioRepository.findById(idUsuario);
        if (opt.isEmpty()) throw new RuntimeException("Usuario no encontrado: " + idUsuario);
        if (!rolRepository.existsById(idRol)) throw new RuntimeException("Rol no encontrado: " + idRol);
        UsuarioModel u = opt.get();
        u.setIdRol(idRol);
        return usuarioRepository.save(u);
    }

    public Direccion agregarDireccion(int idUsuario, Direccion d) {
        if (!usuarioRepository.existsById(idUsuario)) throw new RuntimeException("Usuario no encontrado: " + idUsuario);
        d.setIdUsuario(idUsuario);
        return direccionRepository.save(d);
    }

    public Direccion actualizarDireccion(int idDireccion, Direccion d) {
        Optional<Direccion> opt = direccionRepository.findById(idDireccion);
        if (opt.isEmpty()) throw new RuntimeException("Direccion no encontrada: " + idDireccion);
        Direccion exist = opt.get();
        exist.setCalle(d.getCalle());
        exist.setCiudad(d.getCiudad());
        exist.setProvincia(d.getProvincia());
        exist.setCodigoPostal(d.getCodigoPostal());
        exist.setPais(d.getPais());
        return direccionRepository.save(exist);
    }

    public void eliminarUsuario(int id) { usuarioRepository.deleteById(id); }
}
