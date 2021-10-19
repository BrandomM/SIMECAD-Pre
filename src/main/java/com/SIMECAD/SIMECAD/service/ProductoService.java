
package com.SIMECAD.SIMECAD.service;

import com.SIMECAD.SIMECAD.domain.Producto;
import java.util.List;


public interface ProductoService {
    
    public List<Producto> listarProductos();
    
    public List<Producto> buscarProductosPorNombre(String nombre);
    
    public List<String> listarCategorias();
    
    public Producto registrarProducto(Producto producto);
    
    public Producto modificarProducto(Producto producto);
    
    public Producto buscarProducto(Producto producto);
    
    public void eliminarProducto(Producto producto);

}
