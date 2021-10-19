package com.SIMECAD.SIMECAD.dao;

import com.SIMECAD.SIMECAD.domain.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioDao extends CrudRepository<Usuario, Long> {

    @Query(value = "SELECT * FROM tbl_usuarios WHERE correo = ?1 AND contrasena = ?2 LIMIT 1", nativeQuery = true)
    public Usuario validarUsuario(String correo, String contrasena);
    
    @Query(value = "SELECT * FROM tbl_usuarios WHERE correo = ?1 LIMIT 1", nativeQuery = true)
    public Usuario buscarUsuarioPorCorreo(String correo);

    @Query(value = "SELECT * FROM tbl_usuarios WHERE CONCAT(primer_nombre, \" \", segundo_nombre, \" \", primer_apellido, \" \", segundo_apellido) LIKE %?1%", nativeQuery = true)
    public List<Usuario> buscarUsuariosPorNombre(String nombre);
        
}
