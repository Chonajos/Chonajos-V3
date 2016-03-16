
package com.web.chon.service;

import com.web.chon.dominio.Venta;

/**
 *
 * Interface para el servicio Ventas
 * @author Juan de la Cruz
 */
public interface IfaceVenta {
    
    /**
     * Inserta una venta
     * @param subProducto
     * @return 
     */
    public int insertarVenta(Venta subProducto);
    
    /**
     * Regresa el siguiente id de venta a insertar
     * @return 
     */
    public int getNextVal();
    
}
