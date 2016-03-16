package com.web.chon.negocio;

import com.web.chon.dominio.Subproducto;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Juan de la Cruz
 */
@Remote
public interface NegocioSubProducto {
    
    /**
     * Regresa una lista con todos los productos que estan en la bd
     * @return 
     */
    public List<Object[]> getSubProductos();
    
    /**
     * Elimina un producto de la bd
     * @param idProducto
     * @return 0 si no se pudo realizar y 1 si se realizo
     */
    public int deleteSubProducto(String idProducto);
    /**
     * Inserta un producto en la bd
     * @param subProducto
     * @return regresa 0 si no se realizo la insertcion y 1 si se realizo correctamente
     */
    public int insertarSubProducto(Subproducto subProducto);
    
    /**
     * Modifica un producto en la bd
     * @param subProducto
     * @return regresa 0 sino se realizo la modificacion y 1 si se realizo correctamente
     */
    public int updateSubProducto(Subproducto subProducto);
    
    /**
     * Devuelve el id del siguiente id a registrar en productos 
     * @param idCategoria
     * @return 
     */
    public int getLastIdProducto(String idCategoria);
    
    /**
     * Obtiene un producto por medio de sus id
     * @param idProducto
     * @return 
     */
    public List<Object[]> getProductoById(String idProducto);
    
    /**
     * 
     * @param idProducto
     * @return 
     */
    public List<Object[]> getSubProductoByNombre(String idProducto);
    

}
