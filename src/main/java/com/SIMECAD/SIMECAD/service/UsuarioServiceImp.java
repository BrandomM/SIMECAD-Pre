package com.SIMECAD.SIMECAD.service;

import com.SIMECAD.SIMECAD.dao.UsuarioDao;
import com.SIMECAD.SIMECAD.domain.Usuario;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImp implements UsuarioService {

    @Autowired
    private UsuarioDao usuarioDao;

    @Override
    public List<Usuario> listarUsuarios() {
        return (List<Usuario>) usuarioDao.findAll();
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {

        if (usuarioDao.buscarUsuarioPorCorreo(usuario.getCorreo()) == null) {

            if (usuario.getRol() == null) {
                usuario.setRol("Cliente");
            }
            if (usuario.getPrimerNombre().split(" ").length > 1) {
                String[] nombres = usuario.getPrimerNombre().split(" ");
                usuario.setPrimerNombre(nombres[0]);
                usuario.setSegundoNombre(nombres[1]);
            }
            if (usuario.getPrimerApellido().split(" ").length > 1) {
                String[] apellidos = usuario.getPrimerApellido().split(" ");
                usuario.setPrimerApellido(apellidos[0]);
                usuario.setSegundoApellido(apellidos[1]);
            }
            if (usuario.getSegundoNombre() == null) {
                usuario.setSegundoNombre("");
            }
            if (usuario.getSegundoApellido() == null) {
                usuario.setSegundoApellido("");
            }
            return usuarioDao.save(usuario);
        }
        return null;
    }

    @Override
    public Usuario modificarUsuario(Usuario usuario) {

        if (usuario.getRol() == null) {
            usuario.setRol("Cliente");
        }
        if (usuario.getPrimerNombre().split(" ").length > 1) {
            String[] nombres = usuario.getPrimerNombre().split(" ");
            usuario.setPrimerNombre(nombres[0]);
            usuario.setSegundoNombre(nombres[1]);
        }
        if (usuario.getPrimerApellido().split(" ").length > 1) {
            String[] apellidos = usuario.getPrimerApellido().split(" ");
            usuario.setPrimerApellido(apellidos[0]);
            usuario.setSegundoApellido(apellidos[1]);
        }
        if (usuario.getSegundoNombre() == null) {
            usuario.setSegundoNombre("");
        }
        if (usuario.getSegundoApellido() == null) {
            usuario.setSegundoApellido("");
        }
        return usuarioDao.save(usuario);
    }

    @Override
    public Usuario buscarUsuario(Usuario usuario) {
        return usuarioDao.findById(usuario.getIdUsuario()).orElse(null);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) {
        usuarioDao.delete(usuario);
    }

    @Override
    public Usuario validarUsuario(String correo, String contrasena) {

        Usuario usuario = usuarioDao.validarUsuario(correo, contrasena);
        if (usuario != null) {
            return usuario;
        } else {
            return null;
        }

    }

    @Override
    public List<Usuario> buscarUsuariosPorNombre(String nombre) {
        return usuarioDao.buscarUsuariosPorNombre(nombre);
    }

}
