package com.web.chon.negocio;

import com.web.chon.dominio.Producto;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioProducto {
    
    public List<Object[]> getProductos();
    public int deleteProducto(String idProducto);
    public int insertarProducto(Producto producto);
    public int updateProducto(Producto producto);
    /**
     * Obtiene el ultimo id de categoria y lo regresa como entero
     * @return 
     */
    public int getLastIdCategoria();
    

}
