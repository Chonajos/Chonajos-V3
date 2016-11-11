
package com.web.chon.service;
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
    public ArrayList<Venta> getVentasByIntervalDate(Date fechaInicio, Date fechaFin,BigDecimal idSucursal,BigDecimal idStatusVenta,String idproducto,BigDecimal idTipoVenta,BigDecimal idCliente);
    
    
    /**
     * obtiene el ultimo folio de venta por id de sucursal
     */
    
    public int getFolioByIdSucursal(int idSucursal);
    
    public int cancelarVenta(int idVenta,int idUsuario,String comentarios);
    
    
    public BigDecimal getTotalVentasByDay(String fecha);

}
