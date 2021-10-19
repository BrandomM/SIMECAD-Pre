package com.SIMECAD.SIMECAD.service;

import com.SIMECAD.SIMECAD.domain.Usuario;
import java.util.List;

public interface UsuarioService {

    public List<Usuario> listarUsuarios();

    public Usuario registrarUsuario(Usuario usuario);
    
    public Usuario modificarUsuario(Usuario usuario);

    public Usuario buscarUsuario(Usuario usuario);

    public void eliminarUsuario(Usuario usuario);

    public Usuario validarUsuario(String correo, String contrasena);
    
    public List<Usuario> buscarUsuariosPorNombre(String nombre);

}
