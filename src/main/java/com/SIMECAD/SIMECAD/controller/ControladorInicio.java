package com.SIMECAD.SIMECAD.controller;

import com.SIMECAD.SIMECAD.domain.Producto;
import com.SIMECAD.SIMECAD.domain.Usuario;
import com.SIMECAD.SIMECAD.service.ProductoServiceImp;
import com.SIMECAD.SIMECAD.service.UsuarioServiceImp;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ControladorInicio {

    static Usuario usuario;
    static final String PROJECT_FOLDER = System.getProperty("user.dir");

    @Autowired
    private UsuarioServiceImp usuarioServiceImp;

    @Autowired
    private ProductoServiceImp productoServiceImp;

    @GetMapping("/")
    public String inicio(Model model, Usuario usuario) {

        ControladorInicio.usuario = null;
        return "index";
    }

    @PostMapping("/registrarUsuario")
    public String registrarUsuario(Usuario usuario) {

        usuarioServiceImp.registrarUsuario(usuario);

        if (ControladorInicio.usuario != null) {
            return "redirect:/AdministradorUsuarios";
        }

        return "redirect:/";
    }

    @PostMapping("/ingresar")
    public String ingresar(Model model, Usuario usuario) {

        String correo = usuario.getCorreo();
        String contrasena = usuario.getContrasena();
        usuario = usuarioServiceImp.validarUsuario(correo, contrasena);

        ControladorInicio.usuario = usuario;

        if (usuario == null) {
            return "redirect:/";
        }
        String rol = usuario.getRol();
        switch (rol) {
            case "Administrador":
                model.addAttribute("usuarioLog", usuario);
                return "Administrador/adminPrincipal";
            case "Empleado":
                model.addAttribute("usuarioLog", usuario);
                return "Empleado/empleadoPrincipal";
            case "Cliente":
                model.addAttribute("usuarioLog", usuario);
                return "Cliente/clientePrincipal";
            default:
                return "redirect:/";
        }
    }

    @GetMapping("/Servicios")
    public String servicios(Model model, Usuario usuario) {

        return "servicios";
    }

    @GetMapping("/Productos")
    public String productos(Model model, Usuario usuario) {

        List<Producto> listaProductos = productoServiceImp.listarProductos();
        model.addAttribute("listaProductos", listaProductos);

        return "productos";
    }

    @GetMapping("/SobreNosotros")
    public String sobreNosotros(Model model, Usuario usuario) {

        return "sobreNosotros";
    }

    @GetMapping("/Contacto")
    public String contacto(Model model, Usuario usuario) {

        return "contacto";
    }

    @GetMapping("/AdministradorPrincipal")
    public String administradorPrincipal(Model model, Usuario usuario) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        return "Administrador/adminPrincipal";
    }

//    **************************************************************************
//    MÓDULO DE USUARIOS
//    **************************************************************************
    @GetMapping("/AdministradorUsuarios")
    public String administradorUsuarios(Model model, Usuario usuario) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }
        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        List<Usuario> listaPersonas = usuarioServiceImp.listarUsuarios();
        model.addAttribute("listaPersonas", listaPersonas);

        return "Administrador/adminUsuarios";
    }

    @PostMapping("/EncontrarUsuariosPorNombre")
    public String encontrarUsuariosPorNombre(Model model, Usuario usuario, @RequestParam(value = "nombre") String nombre) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }
        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        List<Usuario> listaPersonas = usuarioServiceImp.buscarUsuariosPorNombre(nombre);
        model.addAttribute("listaPersonas", listaPersonas);

        return "Administrador/adminUsuarios";
    }

    @GetMapping("/AdministradorCrearUsuario")
    public String administradorCrearUsuario(Model model, Usuario usuario) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }
        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        return "Administrador/adminCrearUsuario";
    }

    @PostMapping("/AdministradorRegistrarUsuario")
    public String administradorRegistrarUsuario(Model model, Usuario usuario, @RequestParam(value = "archivo") MultipartFile archivo) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        usuario = usuarioServiceImp.registrarUsuario(usuario);

        if (!archivo.isEmpty()) {
            try {
                String nombreImagen = "/img/Usuarios/usuario" + usuario.getIdUsuario() + archivo.getOriginalFilename().substring(archivo.getOriginalFilename().lastIndexOf("."));
                archivo.transferTo(new File(PROJECT_FOLDER + "/src/main/resources/static" + nombreImagen));

                usuario.setImagen(nombreImagen);
                usuarioServiceImp.modificarUsuario(usuario);

            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/AdministradorUsuarios";
            }
        }

        return "redirect:/AdministradorUsuarios";
    }

    @GetMapping("/AdministradorModificarUsuario/{idUsuario}")
    public String administradorModificarUsuario(Model model, Usuario usuario) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }
        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        usuario = usuarioServiceImp.buscarUsuario(usuario);
        model.addAttribute("usuario", usuario);

        return "Administrador/adminModificarUsuario";
    }

    @PostMapping("/AdministradorActualizarUsuario")
    public String administradorActualizarUsuario(Model model, Usuario usuario, @RequestParam(value = "archivo") MultipartFile archivo) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        usuario = usuarioServiceImp.modificarUsuario(usuario);

        if (!archivo.isEmpty()) {
            try {
                String nombreImagen = "/img/Usuarios/usuario" + usuario.getIdUsuario() + archivo.getOriginalFilename().substring(archivo.getOriginalFilename().lastIndexOf("."));
                archivo.transferTo(new File(PROJECT_FOLDER + "/src/main/resources/static" + nombreImagen));

                usuario.setImagen(nombreImagen);
                usuarioServiceImp.modificarUsuario(usuario);

            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/AdministradorUsuarios";
            }
        }

        return "redirect:/AdministradorUsuarios";
    }

    @GetMapping("/AdministradorEliminarUsuario/{idUsuario}")
    public String administradorEliminarUsuario(Model model, Usuario persona) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        usuarioServiceImp.eliminarUsuario(persona);

        return "redirect:/AdministradorUsuarios";
    }

//    **************************************************************************
//    MÓDULO DE VENTAS
//    **************************************************************************
    @GetMapping("/AdministradorInventario")
    public String administradorInventario(Model model) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }
        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        List<Producto> listaProductos = productoServiceImp.listarProductos();
        model.addAttribute("listaProductos", listaProductos);

        return "Administrador/adminInventario";
    }

    @PostMapping("/EncontrarProductosPorNombre")
    public String encontrarProductosPorNombre(Model model, @RequestParam(value = "nombre") String nombre) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }
        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        List<Producto> listaProductos = productoServiceImp.buscarProductosPorNombre(nombre);
        model.addAttribute("listaProductos", listaProductos);

        return "Administrador/adminInventario";
    }

    @GetMapping("/AdministradorCrearProducto")
    public String administradorCrearUsuario(Model model, Producto producto) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }
        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        List<String> listaCategorias = productoServiceImp.listarCategorias();
        model.addAttribute("listaCategorias", listaCategorias);

        return "Administrador/adminCrearProducto";
    }

    @PostMapping("/AdministradorRegistrarProducto")
    public String administradorRegistrarProducto(Model model, Producto producto, @RequestParam(value = "archivo") MultipartFile archivo) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        producto = productoServiceImp.registrarProducto(producto);

        if (!archivo.isEmpty()) {
            try {
                String nombreImagen = "/img/Productos/producto" + producto.getIdProducto() + archivo.getOriginalFilename().substring(archivo.getOriginalFilename().lastIndexOf("."));
                archivo.transferTo(new File(PROJECT_FOLDER + "/src/main/resources/static" + nombreImagen));

                producto.setImagen(nombreImagen);
                productoServiceImp.modificarProducto(producto);

            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/AdministradorInventario";
            }
        }

        return "redirect:/AdministradorInventario";
    }

    @GetMapping("/AdministradorModificarProducto/{idProducto}")
    public String administradorModificarProducto(Model model, Producto producto) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }
        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        producto = productoServiceImp.buscarProducto(producto);
        model.addAttribute("producto", producto);

        List<String> listaCategorias = productoServiceImp.listarCategorias();
        model.addAttribute("listaCategorias", listaCategorias);

        return "Administrador/adminModificarProducto";
    }

    @PostMapping("/AdministradorActualizarProducto")
    public String administradorActualizarProducto(Model model, Producto producto, @RequestParam(value = "archivo") MultipartFile archivo) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        producto = productoServiceImp.modificarProducto(producto);

        if (!archivo.isEmpty()) {
            try {
                String nombreImagen = "/img/Productos/producto" + producto.getIdProducto() + archivo.getOriginalFilename().substring(archivo.getOriginalFilename().lastIndexOf("."));
                archivo.transferTo(new File(PROJECT_FOLDER + "/src/main/resources/static" + nombreImagen));

                producto.setImagen(nombreImagen);
                productoServiceImp.modificarProducto(producto);

            } catch (Exception e) {
                e.printStackTrace();
                return "redirect:/AdministradorInventario";
            }
        }

        return "redirect:/AdministradorInventario";
    }

    @GetMapping("/AdministradorEliminarProducto/{idProducto}")
    public String administradorEliminarUsuario(Model model, Producto producto) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        productoServiceImp.eliminarProducto(producto);

        return "redirect:/AdministradorInventario";
    }

//    **************************************************************************
//    NAVEGACIÓN CLIENTE
//    **************************************************************************
    @GetMapping("/ClientePrincipal")
    public String clientePrincipal(Model model) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        return "Cliente/clientePrincipal";
    }

    @GetMapping("/ClienteServicios")
    public String clienteServicios(Model model) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        return "Cliente/clienteServicios";
    }

    @GetMapping("/ClienteProductos")
    public String clienteProductos(Model model) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        List<Producto> listaProductos = productoServiceImp.listarProductos();
        model.addAttribute("listaProductos", listaProductos);

        return "Cliente/clienteProductos";
    }

    @GetMapping("/ClienteSobreNosotros")
    public String clienteSobreNosotros(Model model) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        return "Cliente/clienteSobreNosotros";
    }

    @GetMapping("/ClienteContacto")
    public String clienteContacto(Model model) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        return "Cliente/clienteContacto";
    }

//    **************************************************************************
//    NAVEGACIÓN EMPLEADO
//    **************************************************************************
    @GetMapping("/EmpleadoPrincipal")
    public String empleadoPrincipal(Model model) {

        if (ControladorInicio.usuario == null) {
            return "redirect:/";
        }

        model.addAttribute("usuarioLog", ControladorInicio.usuario);

        return "Empleado/empleadoPrincipal";
    }

}
