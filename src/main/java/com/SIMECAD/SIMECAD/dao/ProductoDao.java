
package com.SIMECAD.SIMECAD.dao;

import com.SIMECAD.SIMECAD.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface ProductoDao extends CrudRepository<Producto, Long>{
    
    @Query(value = "SELECT * FROM tbl_productos WHERE nombre LIKE %?1%", nativeQuery = true)
    public List<Producto> buscarProductosPorNombre(String nombre);
    
    @Query(value = "SELECT DISTINCT(categoria) AS categorias FROM tbl_productos ORDER BY categorias;" , nativeQuery = true)
    public List<String> listarCategorias();
    
}
