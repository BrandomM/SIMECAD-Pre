
package com.SIMECAD.SIMECAD.service;

import com.SIMECAD.SIMECAD.dao.ProductoDao;
import com.SIMECAD.SIMECAD.domain.Producto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImp implements ProductoService {

    @Autowired
    ProductoDao productoDao;
    
    @Override
    public List<Producto> listarProductos() {
        return (List<Producto>) productoDao.findAll();
    }

    @Override
    public List<Producto> buscarProductosPorNombre(String nombre) {
        return (List<Producto>) productoDao.buscarProductosPorNombre(nombre);
    }

    @Override
    public List<String> listarCategorias() {
        return productoDao.listarCategorias();
    }

    @Override
    public Producto registrarProducto(Producto producto) {
        return productoDao.save(producto);
    }

    @Override
    public Producto modificarProducto(Producto producto) {
        return productoDao.save(producto);
    }

    @Override
    public Producto buscarProducto(Producto producto) {
        return productoDao.findById(producto.getIdProducto()).orElse(null);
    }

    public void eliminarProducto(Producto producto) {
        productoDao.delete(producto);
    }
    
}
