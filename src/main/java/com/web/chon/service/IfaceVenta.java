
package com.web.chon.service;

import com.web.chon.dominio.RelacionOperaciones;
import com.web.chon.dominio.Venta;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

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
    public int insertarVenta(Venta subProducto,int folioVenta);
    
    /**
     * Regresa el siguiente id de venta a insertar
     * @return 
     */
    public int getNextVal();
    
    /**
     * Obtiene lista de las ventas que se hicieron desde la fecha inicio asta la fecha fin
     * @param fechaInicio
     * @param fechaFin
     * @return 
     */
    public ArrayList<RelacionOperaciones> getVentasByIntervalDate(Date fechaInicio, Date fechaFin,int idSucursal,int idStatusVenta);
    
    
    /**
     * obtiene el ultimo folio de venta por id de sucursal
     */
    
    public int getFolioByIdSucursal(int idSucursal);
}
